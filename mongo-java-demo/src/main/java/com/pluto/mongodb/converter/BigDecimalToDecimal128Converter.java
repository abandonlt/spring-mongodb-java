package com.pluto.mongodb.converter;

import org.bson.json.StrictJsonWriter;
import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * Description:
 * Author: Administrator
 * Date:2018-03-31 下午 05:14
 */
public class BigDecimalToDecimal128Converter implements Converter<BigDecimal, Decimal128> {

    @Override
    public Decimal128 convert(BigDecimal bigDecimal) {
        return new Decimal128(bigDecimal);
    }
}
