package com.china;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Producer {
    //用于存放电话号码，准备产生随机数使用
    private List<String> phoneList = null;
    //存放联系人电话与姓名的映射
    private Map<String, String> contacts = null;

    private void initContacts() {
        phoneList = new ArrayList<String>();
        contacts = new HashMap<String, String>();

        phoneList.add("15705533372");
        phoneList.add("15710055337");
        phoneList.add("13393208961");
        phoneList.add("16603543759");
        phoneList.add("13866986781");
        phoneList.add("14022263574");
        phoneList.add("19239973882");
        phoneList.add("15133633876");
        phoneList.add("17108702639");
        phoneList.add("15620615630");
        phoneList.add("19852279421");
        phoneList.add("18329792289");
        phoneList.add("19935509490");
        phoneList.add("15178526457");
        phoneList.add("15481881203");
        phoneList.add("13351295515");
        phoneList.add("16191880259");
        phoneList.add("17204579002");
        phoneList.add("19279995715");
        phoneList.add("16770537882");
        phoneList.add("17049562599");
        phoneList.add("17095917967");
        phoneList.add("13619731839");
        phoneList.add("13413245735");
        phoneList.add("18680954095");

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

    private String productLog() {
        String call1 = null;
        String call2 = null;
        //[0,20)生成25个电话号码
        call1 = phoneList.get((int) (Math.random() * phoneList.size()));
        while (true) {
            call2 = phoneList.get((int) (Math.random() * phoneList.size()));
            if (!call1.equals(call2)) break;
        }
        //生成通话建立时间
        String data_time = randomDate("2017-01-01", "2017-12-32");
        //通话建立时间的时间戳表达形式
        //String data_time_ts = null;
        //生成通话持续时间为[0,1201)之间的整数
        int dura = (int) ((Math.random() * 60 * 20) + 1);
        //格式化通话时长，保证每个时间都是四位的
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        String duration = decimalFormat.format(dura);
        //这个标记可以省略，标记意义为主叫号码
        //String flag = "1";
        String result = call1 + "," + call2 + "," + data_time + "," + duration + "\r\n";
        System.out.print(result);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String randomDate(String startDate, String stopDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = simpleDateFormat.parse(startDate);
            Date stop = simpleDateFormat.parse(stopDate);
            //判断时间合法性
            if (start.getTime() >= stop.getTime()) return null;

            long point = start.getTime() + (long) (Math.random() * (stop.getTime() - start.getTime()));
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String result = sdf2.format(point);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeLog(String filePath, Producer producer) {
        OutputStreamWriter outputStreamWriter = null;
        try {
            OutputStream outputStream = new FileOutputStream(filePath, false);
            outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            while (true) {
                String log = productLog();
                outputStreamWriter.write(log);
                outputStreamWriter.flush();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        if (args == null || args.length <= 0) {
            System.out.println("no arguments");
            System.exit(1);
        }

        Producer producer = new Producer();
        producer.initContacts();
        producer.writeLog(args[0], producer);
    }
}
