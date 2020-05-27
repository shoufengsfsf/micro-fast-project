package com.micro.fast.utils.address;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 根据ip查询地址
 *
 * @author shoufeng
 */
@Slf4j
public class AddressUtils {
    private static DbConfig dbConfig;
    private static DbSearcher dbSearcher;

    static {
        try {
            dbConfig = new DbConfig();
            dbSearcher = new DbSearcher(new DbConfig(), System.getProperty("user.dir") + "/ip2region/ip2region.db");
        } catch (DbMakerConfigException | FileNotFoundException e) {
            log.error("初始化ip2region.DbConfig、ip2region.DbSearcher失败", e);
        }
    }

    /**
     * 通过ip获取所在区域
     */
    public static String getRegionByIp(String ip) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int algorithm = DbSearcher.BTREE_ALGORITHM;

        Method method = getMethodByAlgorithm(algorithm);
        if (ObjectUtils.isEmpty(method)) {
            return null;
        }

        DataBlock dataBlock = (DataBlock) method.invoke(dbSearcher, ip);

        return dataBlock.getRegion();
    }

    private static Method getMethodByAlgorithm(int algorithm) throws NoSuchMethodException {
        Method method = null;
        switch (algorithm) {
            case DbSearcher.BTREE_ALGORITHM: {
                method = dbSearcher.getClass().getMethod("btreeSearch", String.class);
                break;
            }
            case DbSearcher.BINARY_ALGORITHM: {
                method = dbSearcher.getClass().getMethod("binarySearch", String.class);
                break;
            }
            case DbSearcher.MEMORY_ALGORITYM: {
                method = dbSearcher.getClass().getMethod("memorySearch", String.class);
                break;
            }
            default: {
                log.error("无效algorithm: {}", algorithm);
                break;
            }
        }
        return method;
    }

}
