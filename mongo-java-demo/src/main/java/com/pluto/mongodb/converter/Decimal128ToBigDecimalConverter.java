package com.pluto.mongodb.converter;


import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * Description:
 * Author: Administrator
 * Date:2018-03-31 下午 05:18
 */
public class Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {


    @Override
    public BigDecimal convert(Decimal128 decimal128) {
        return decimal128.bigDecimalValue();
    }
}
