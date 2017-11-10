package com.china.analysis.converter;

import com.china.analysis.kv.base.BaseDimension;

import java.io.Closeable;
import java.io.IOException;

public interface IConverter extends Closeable{
    // 根据传入的dimension对象，获取数据库中对应该对象数据的id，如果不存在，则插入该数据再返回
    int getDimensionId(BaseDimension dimension) throws IOException;
}
