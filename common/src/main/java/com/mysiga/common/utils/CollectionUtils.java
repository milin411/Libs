package com.mysiga.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * collection utils
 *
 * @author wilson.wu
 */
public class CollectionUtils {


    /**
     * invalid collection
     *
     * @param collection
     * @return
     */
    public static boolean isInvalid(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * valid collection
     *
     * @param collection
     * @return
     */
    public static boolean isValid(Collection<?> collection) {
        return !isInvalid(collection);
    }

    /**
     * invalid map
     *
     * @param map
     * @return
     */
    public static boolean isInvalid(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * valid map
     *
     * @param map
     * @return
     */
    public static boolean isValid(Map map) {
        return !isInvalid(map);
    }
}
