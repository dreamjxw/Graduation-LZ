package com.lz.design.common;

/**
 * @author Xingwu.Jia
 * @date 2018/5/19 22:20
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

public class PropertyUtil {

    /**
     * 日志类。
     */
    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * 环境通用属性文件名
     */
    public static final String COMMON_PROPERTY_FILE = "common.properties";
    /**
     * 默认属性文件名
     */
    public static final String DEFAULT_PROPERTY_FILE = "config.properties";

    /**
     * 附加文件的前缀
     */
    private static final String ADD_PROPERTY_PREFIX = "add.property.file.";

    /**
     * 属性文件扩展名
     */
    private static final String PROPERTY_EXTENSION = ".properties";

    /**
     * 属性键值的保存对象
     */
    private static TreeMap<String, String> props = new TreeMap<String, String>();

    /**
     * 已读入的属性文件名列表
     */
    private static Set<String> files = new HashSet<String>();

    /**
     * 类加载时，进行文件属性读取的初始化。
     */
    static {
        StringBuilder key = new StringBuilder();
        load(DEFAULT_PROPERTY_FILE);
        load(COMMON_PROPERTY_FILE);
        if (props != null) {
            for (int i = 1; ; i++) {
                key.setLength(0);
                key.append(ADD_PROPERTY_PREFIX);
                key.append(i);
                String path = getProperty(key.toString());
                if (path == null) {
                    break;
                }
                addPropertyFile(path);
            }
        }
        overrideProperties();
    }

    /**
     * 读取指定的属性文件。
     * <p/>
     * <p>
     * 读取并追加指定属性文件的内容。
     * </p>
     *
     * @param name 属性文件名
     */
    private static void load(String name) {
        StringBuilder key = new StringBuilder();
        Properties p = readPropertyFile(name);
        for (Entry<Object, Object> e : p.entrySet()) {
            // 追加读入的所有内容
            props.put((String) e.getKey(), (String) e.getValue());
        }

        for (int i = 1; ; i++) {
            key.setLength(0);
            key.append(ADD_PROPERTY_PREFIX);
            key.append(i);
            String addfile = p.getProperty(key.toString());
            if (addfile == null) {
                break;
            }
            String path = getPropertiesPath(name, addfile);
            addPropertyFile(path);
        }
    }

    /**
     * 读取指定的属性文件。
     * <p/>
     * <p>
     * 读取并追加指定属性文件的内容。
     * </p>
     *
     * @param name 属性文件名
     * @return 属性列表
     */
    private static Properties readPropertyFile(String name) {
        // 获取当前容器的类加载器，并读取WEB-INF/classes下的属性文件。
        // 或使用主线程的类加载器，通过JNLP方式获取资源内容。
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        if (is == null) {
            is = PropertyUtil.class.getResourceAsStream("/" + name);
        }
        InputStreamReader reader = null;
        if (is != null) {
            reader = new InputStreamReader(is);
        }

        Properties p = new Properties();
        try {
            try {
                p.load(reader);
                files.add(name);

            } catch (NullPointerException e) {
                logger.error("!!! PANIC: Cannot load {} !!!", name, e);
            } catch (IOException e) {
                logger.error("!!! PANIC: Cannot load {} !!!", name, e);
            }
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        return p;
    }

    /**
     * 通过命令行选项&quot;-D&quot; 覆盖从属性文件中读取的系统属性内容。
     */
    private static void overrideProperties() {
        Enumeration<String> enumeration = Collections.enumeration(props.keySet());
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = System.getProperty(name);
            if (value != null) {
                props.put(name, value);
            }
        }
    }

    /**
     * 读取指定的追加属性文件的内容。
     * <p/>
     * <p>
     * 每个文件内容只读取一次，重复执行该方法无效。
     * 属性文件扩展名".properties"可省略。
     * </p>
     *
     * @param name 属性文件名
     */
    public static void addPropertyFile(String name) {
        String newName = name;
        if (!name.endsWith(PROPERTY_EXTENSION)) {
            StringBuilder nameBuf = new StringBuilder();
            nameBuf.append(name);
            nameBuf.append(PROPERTY_EXTENSION);
            newName = nameBuf.toString();
        }
        if (!files.contains(newName)) {
            load(newName);
        }
    }

    /**
     * 获取指定key的属性值。
     * <p/>
     * <p>
     * 当参数由&quot;@&quot;开头时，属性值被看作一个间接引用，并做为一个新属性名重新搜索。
     * 为避免<code>key=@key</code>情况下的死循环，直接将<code>@key</code>结果返回。
     * 当需要设置以&quot;@&quot;开头的属性值时，请使用&quot;@@&quot;方式避免间接属性名引用。
     * </p>
     *
     * @param key 属性名
     * @return 指定属性名的属性值
     */
    public static String getProperty(String key) {
        String result = props.get(key);

        // (key)=@(key)时，避免死循环
        if (result != null && result.equals("@" + key)) {
            return result;
        }
        // 属性值以@@开头时，将结果看作时一个@开头的字符串返回
        if (result != null && result.startsWith("@@")) {
            return result.substring(1);
        }
        if (result != null && result.startsWith("@")) {
            result = getProperty(result.substring(1));
        }

        return result;
    }

    /**
     * 获取指定key的属性值。
     * <p/>
     * <p>
     * 属性值未找到时，返回指定的默认值。
     * </p>
     *
     * @param key          属性名
     * @param defaultValue 属性默认值
     * @return 指定属性名的属性值
     */
    public static String getProperty(String key, String defaultValue) {
        String result = props.get(key);
        if (result == null) {
            return defaultValue;
        }
        return result;
    }

    /**
     * 获取全部属性名。
     *
     * @return 属性名列表
     */
    public static Enumeration<String> getPropertyNames() {
        return Collections.enumeration(props.keySet());
    }

    /**
     * 获取以指定前缀为开头的属性名。
     *
     * @param keyPrefix 属性名前缀
     * @return 以指定前缀的开头的属性名列表
     */
    public static Enumeration<String> getPropertyNames(String keyPrefix) {
        Map<String, String> map = props.tailMap(keyPrefix);
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            if (!name.startsWith(keyPrefix)) {
                return Collections.enumeration(props.subMap(keyPrefix, name).keySet());
            }
        }
        return Collections.enumeration(map.keySet());
    }

    /**
     * 获取指定属性文件中，以指定前缀为开头的属性名对应的属性值集合。
     *
     * @param propertyName 属性文件名
     * @param keyPrefix    属性名前缀
     * @return 属性值集合
     */
    public static Set<String> getPropertiesValues(String propertyName,
                                                  String keyPrefix) {

        Properties localProps = loadProperties(propertyName);
        if (localProps == null) {
            return null;
        }

        Enumeration<String> keyEnum = getPropertyNames(localProps, keyPrefix);
        if (keyEnum == null) {
            return null;
        }

        return getPropertiesValues(localProps, keyEnum);
    }

    /**
     * 获取指定属性集中以给定前缀为开头属性名列表。
     *
     * @param localProps 属性集
     * @param keyPrefix  属性名前缀
     * @return 与属性名前缀一致的属性名
     */
    public static Enumeration<String> getPropertyNames(
            Properties localProps, String keyPrefix) {

        if (localProps == null || keyPrefix == null) {
            return null;
        }

        Collection<String> matchedNames = new ArrayList<String>();
        Enumeration<?> propNames = localProps.propertyNames();
        while (propNames.hasMoreElements()) {
            String name = (String) propNames.nextElement();
            if (name.startsWith(keyPrefix)) {
                matchedNames.add(name);
            }
        }
        return Collections.enumeration(matchedNames);
    }

    /**
     * 获取属性集中与包含指定属性名集的值。
     *
     * @param localProps    属性集
     * @param propertyNames 属性名集
     * @return 属性值集合
     */
    public static Set<String> getPropertiesValues(Properties localProps,
                                                  Enumeration<String> propertyNames) {

        if (localProps == null || propertyNames == null) {
            return null;
        }

        Set<String> retSet = new HashSet<String>();
        while (propertyNames.hasMoreElements()) {
            retSet.add(localProps.getProperty(propertyNames.nextElement()));
        }
        return retSet;
    }

    /**
     * 根据指定属性文件名，获取属性集。
     *
     * @param propertyName 属性文件名
     * @return 属性集
     */
    public static Properties loadProperties(String propertyName) {
        // propertyName为null或空字符串时，返回null
        if (propertyName == null || "".equals(propertyName)) {
            return null;
        }
        Properties retProps = new Properties();

        StringBuilder resourceName = new StringBuilder();
        resourceName.append(propertyName);
        resourceName.append(PROPERTY_EXTENSION);

        // 获取当前容器的类加载器，并读取WEB-INF/classes下的属性文件。
        // 或使用主线程的类加载器，通过JNLP方式获取资源内容。
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(resourceName.toString());
        if (is == null) {
            is = PropertyUtil.class.getResourceAsStream("/" + propertyName + PROPERTY_EXTENSION);
        }
        InputStreamReader reader = null;
        if (is != null) {
            reader = new InputStreamReader(is);
        }

        try {
            retProps.load(reader);
        } catch (NullPointerException npe) {
            logger.warn("*** Can not find property-file [" + propertyName + ".properties] ***", npe);
            retProps = null;
        } catch (IOException ie) {
            logger.error("", ie);
            retProps = null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ie) {
                logger.error("", ie);
                retProps = null;
            }
        }
        return retProps;
    }

    /**
     * 获取追加属性文件的读取路径。
     * <p/>
     * 以属性文件存在的目录为基础，返回属性文件中附加文件的读取路径。
     *
     * @param resource 含有附加文件的属性文件
     * @param addFile  追加属性文件
     * @return 追加属性文件的读取路径
     */
    private static String getPropertiesPath(String resource, String addFile) {
        File file = new File(resource);
        String dir = file.getParent();
        if (dir != null) {
            StringBuilder dirBuf = new StringBuilder();
            dirBuf.setLength(0);
            dirBuf.append(dir);
            dirBuf.append(File.separator);
            dir = dirBuf.toString();
        } else {
            dir = "";
        }
        StringBuilder retBuf = new StringBuilder();
        retBuf.setLength(0);
        retBuf.append(dir);
        retBuf.append(addFile);
        return retBuf.toString();
    }
}