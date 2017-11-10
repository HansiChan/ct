package com.china.analysis.mapper;

import com.china.analysis.kv.impl.ComDimension;
import com.china.analysis.kv.impl.ContactDimension;
import com.china.analysis.kv.impl.DateDimension;
import jodd.util.StringUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CountDurationMapper extends TableMapper<ComDimension,Text> {
    //存放联系人电话与姓名的映射
    private Map<String,String> contacts;
    private byte[] family = Bytes.toBytes("f1");
    private ComDimension comDimension = new ComDimension();

    public void initContact() {
        contacts = new  HashMap<String,String>();

        contacts.put("13815146352", "何秀丽");
        contacts.put("18478298024", "陶眉");
        contacts.put("17013212699", "汤之念");
        contacts.put("15145685933", "奚梦娇");
        contacts.put("14682034591", "冯平夏");
        contacts.put("15324560067", "周艳");
        contacts.put("17254864591", "华旭");
        contacts.put("14481093065", "何珊");
        contacts.put("18177339371", "孔凡");
        contacts.put("16757877638", "杨凡");
        contacts.put("15777445856", "吴龙婷");
        contacts.put("15279708605", "许半芹");
        contacts.put("13226826667", "蒋叶");
        contacts.put("14131872498", "陈茜");
        contacts.put("13065926621", "曹君");
        contacts.put("18090066454", "蒋丹");
        contacts.put("16277428307", "司胜英");
        contacts.put("16967220309", "华育");
        contacts.put("13892597881", "曹太群");
        contacts.put("18687696020", "卫小丽");
        contacts.put("13051101958", "陶桂花");
        contacts.put("13475600167", "周元珊");
        contacts.put("17293403830", "冯静娴");
        contacts.put("14070041392", "杨红");
        contacts.put("18158448344", "褚瑶");
    }

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        initContact();
    }

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        //01_15837312345_20170810141024_13738909097_1_0180
        String rowKey = Bytes.toString(value.getRow());
        String[] values = rowKey.split("_");
        String flag = values[4];
        //只拿到主叫数据即可
        if(StringUtil.equals(flag,"0")) return;

        String date_time = values[2];
        String duration = values[5];

        String call1 = values[1];
        String call2 = values[3];

        int year = Integer.valueOf(date_time.substring(0,4));
        int month = Integer.valueOf(date_time.substring(4,6));
        int day = Integer.valueOf(date_time.substring(6,8));

        DateDimension dateDimensionYear = new DateDimension(year, -1, -1);
        DateDimension dateDimensionMonth = new DateDimension(year, month, -1);
        DateDimension dateDimensionDay = new DateDimension(year, month, day);

        //第一个电话号码
        ContactDimension contactDimension1 = new ContactDimension(call1, contacts.get(call1));
        comDimension.setContactDimension(contactDimension1);

        comDimension.setDateDimension(dateDimensionYear);
        context.write(comDimension, new Text(duration));

        comDimension.setDateDimension(dateDimensionMonth);
        context.write(comDimension, new Text(duration));

        comDimension.setDateDimension(dateDimensionDay);
        context.write(comDimension, new Text(duration));

        //第二个电话号码
        ContactDimension contactDimension2 = new ContactDimension(call2, contacts.get(call2));
        comDimension.setContactDimension(contactDimension2);

        comDimension.setDateDimension(dateDimensionYear);
        context.write(comDimension, new Text(duration));

        comDimension.setDateDimension(dateDimensionMonth);
        context.write(comDimension, new Text(duration));

        comDimension.setDateDimension(dateDimensionDay);
        context.write(comDimension, new Text(duration));

    }
}
