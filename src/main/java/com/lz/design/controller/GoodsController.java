package com.lz.design.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.lz.design.common.APIResult;
import com.lz.design.common.base64.ImgBase64;
import com.lz.design.model.*;
import com.lz.design.service.GoodsService;
import com.lz.design.service.LoggersService;
import com.lz.design.service.StockService;
import com.lz.design.service.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  2:03
 */
@CrossOrigin
@Controller
@RequestMapping("/lz/design/goods")
public class GoodsController {
    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StockService stockService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoggersService loggersService;

    @RequestMapping(value = "insertGoods.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult insertGoods(@RequestBody Goods goods, HttpServletRequest req) {
        try {
            String userId = req.getParameter("userId");
            Preconditions.checkArgument(userId != null, "操作人Id不能为空");
            Preconditions.checkArgument(goods.getGoodsClass() != null, "商品类别不能为空");
            Preconditions.checkArgument(goods.getGoodsDescribe() != null, "商品描述不能为空");
            Preconditions.checkArgument(goods.getGoodsExpire() != null, "商品过期日期不能为空");
            Preconditions.checkArgument(goods.getGoodsName() != null, "商品名称不能为空");
            Preconditions.checkArgument(goods.getGoodsPicture() != null, "商品图片不能为空");
            Preconditions.checkArgument(goods.getGoodsPrice() != null, "商品单价不能为空");
            Preconditions.checkArgument(goods.getGoodsStockIn() != null, "商品入库数量不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(goods));
            ImgBase64 base64 = new ImgBase64();
            String substring = goods.getGoodsPicture().substring(22);
            logger.info("Base64,:{}", new Gson().toJson(substring));
            String millis = String.valueOf(DateTime.now().getMillis());
            base64.GenerateImage(substring, millis);
            goods.setGoodsPicture("head/" + millis + ".png");
            User user = userService.selectByUserId(Long.valueOf(userId));
            if (user.getUserPower() != 3) {
                logger.warn("【添加商品】该用户无权限添加商品，：{}", new Gson().toJson(user));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            goods.setGoodsExpire(DateTime.now().toDate());
            int i1 = goodsService.insertGoods(goods);
            Stock stock = new Stock();
            stock.setStockInTime(DateTime.now().toDate());
            Goods goods1 = goodsService.selectGoodsByName(goods.getGoodsName());
            stock.setGoodsId(goods1.getGoodsId());
            stock.setGoodsStockIn(goods.getGoodsStockIn());
            stockService.insertStock(stock);
            if (i1 > 0) {
                logger.info("添加商品成功");
                if (user.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员" + user.getUserName() + "添加了商品" + goods.getGoodsName());
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult("添加商品成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "添加商品失败");
    }

    @RequestMapping(value = "deleteGoods.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult deleteGoods(@RequestBody Integer[] goodsId, HttpServletRequest req) {
        try {
            String userId = req.getParameter("userId");
            Preconditions.checkArgument(userId != null, "操作人Id不能为空");
            Preconditions.checkArgument(goodsId != null, "商品Id不能为空");
            User user = userService.selectByUserId(Long.valueOf(userId));
            if (user.getUserPower() != 3) {
                logger.warn("【删除商品】该用户无权限删除商品，：{}", new Gson().toJson(user));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            int i = stockService.deleteStockByGoodsId(goodsId);
            if (i > 0) {
                int i1 = goodsService.deleteByGoodsId(goodsId);
                if (i1 > 0) {
                    logger.info("删除商品成功");
                    if (user.getUserPower() == 4) {
                        Loggers loggers = new Loggers();
                        loggers.setUserId(user.getUserId());
                        loggers.setLoggerTime(DateTime.now().toDate());
                        loggers.setLoggerMessage("系统管理员" + user.getUserName() + "删除了商品" + goodsId);
                        loggersService.insertLoggers(loggers);
                    }
                    return APIResult.buildSuccessResult("删除商品成功");
                }
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "删除商品失败");
    }

    @RequestMapping(value = "updateGoods.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult updateGoods(@RequestBody Goods goods, HttpServletRequest req) {
        try {
            String userId = req.getParameter("userId");
            Preconditions.checkArgument(userId != null, "操作人Id不能为空");
            Preconditions.checkArgument(goods.getGoodsId() != null, "商品Id不能为空");
            User user = userService.selectByUserId(Long.valueOf(userId));
            if (user.getUserPower() != 3) {
                logger.warn("【更改商品】该用户无权限添加商品，：{}", new Gson().toJson(user));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            logger.info("传入数据,:{}", new Gson().toJson(goods));
            int i = goodsService.updateByGoodsId(goods);
            if (i > 0) {
                logger.info("更改商品属性成功");
                if (user.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员" + user.getUserName() + "修改了商品属性" + goods.getGoodsName());
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult("更改商品属性成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "更改商品属性失败");
    }

    @RequestMapping(value = "selectGoods.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectGoods(@RequestBody RequestModel requestModel) {
        try {
            logger.info("传入数据,:{}", new Gson().toJson(requestModel.getMessage()));
            Integer goodsId = Integer.parseInt(requestModel.getMessage());
            if (goodsId == -1) {
                goodsId = null;
            }
            List<Goods> goodsList = goodsService.selectByGoodsId(goodsId);
            if (goodsList != null) {
                for (Goods goods : goodsList) {
                    if (goods.getGoodsSales() == 1) {
                        goods.setGoodsPrice(goods.getGoodsPrice() * 0.85);
                    }
                }
                logger.info("商品列表获取成功", new Gson().toJson(goodsList));
                return APIResult.buildSuccessResult(goodsList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "商品列表获取失败");
    }

    @RequestMapping(value = "searchGoods.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult searchGoods(@RequestBody RequestModel contents) {
        try {

            logger.info("传入数据,:{}", new Gson().toJson(contents.getMessage()));
            List<Goods> goodsList = goodsService.searchGoods(contents.getMessage());
            if (goodsList != null) {
                logger.info("商品搜索列表获取成功", new Gson().toJson(goodsList));
                return APIResult.buildSuccessResult(goodsList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "商品搜索列表获取失败");
    }

    @RequestMapping(value = "selectGoodsBySomething.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectGoodsBySomething(@RequestBody RequestModel requestModel) {
        try {
            logger.info("传入数据,:{}", new Gson().toJson(requestModel.getMessage()));
            int anInt = Integer.parseInt(requestModel.getMessage());
            List<Goods> goodsList = goodsService.selectGoodsBySomething(anInt);
            if (goodsList != null) {
                logger.info("商品条件搜索列表获取成功", new Gson().toJson(goodsList));
                return APIResult.buildSuccessResult(goodsList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "商品条件搜索列表获取失败");
    }

    @RequestMapping(value = "selectGoodsByExpire.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectGoodsByExpire(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        Preconditions.checkArgument(userId != null, "操作人Id不能为空");
        User user = userService.selectByUserId(Long.valueOf(userId));
        if (user.getUserPower() != 3) {
            logger.warn("【查看快过期商品】该用户无权限查看快过期商品，：{}", new Gson().toJson(user));
            return APIResult.buildFailedResult(-1, "您无权限执行此操作");
        }
        try {
            List<Goods> goodsList = goodsService.selectGoodsByExpire(DateTime.now().toDate());
            if (goodsList != null) {
                logger.info("获取过期商品列表成功", new Gson().toJson(goodsList));
                if (user.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员" + user.getUserName() + "查询了快过期商品" + goodsList);
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult(goodsList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "获取过期商品列表失败");
    }

    @RequestMapping(value = "selectUpGoods.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectUpGoods() {
        try {
            List<Goods> goodsList = goodsService.selectUpGoods();
            if (goodsList != null) {
                logger.info("获取上架商品列表成功,:{}", new Gson().toJson(goodsList));
                return APIResult.buildSuccessResult(goodsList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "获取上架商品列表失败");
    }

    @RequestMapping(value = "selectGoodsEach.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectGoodsEach(@RequestBody Integer[] goods) {
        try {
            List<Goods> goodsList = goodsService.selectByEach(goods);
            if (goodsList != null) {
                logger.info("获取上架商品列表成功", new Gson().toJson(goodsList));
                return APIResult.buildSuccessResult(goodsList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【商品模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【商品模块】商品模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "获取上架商品列表失败");
    }
}
