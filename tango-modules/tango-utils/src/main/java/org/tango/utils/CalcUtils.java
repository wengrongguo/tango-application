/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.utils;

/**
 * 计算器
 *
 * @author tango
 * @since 1.5+
 */
public final class CalcUtils {

    /**
     * 数学
     */
    public static class Math {

        /**
         * 加法[与+一致]
         *
         * @param nums
         * @return 运算结果
         */
        public static int addition(int... nums) {
            int result = 0;
            for (int i = 0; i < nums.length; i++) {
                result += nums[i];
            }
            return result;
        }

        /**
         * 减法[与-一致]
         *
         * @param nums
         * @return 运算结果
         */
        public static int subtraction(int... nums) {
            int result = nums[0];
            for (int i = 1; i < nums.length; i++) {
                result -= nums[i];
            }
            return result;
        }

        /**
         * 乘法[与*一致]
         *
         * @param nums
         * @return 运算结果
         */
        public static int multiplication(int... nums) {
            int result = nums[0];
            for (int i = 1; i < nums.length; i++) {
                result *= nums[i];
            }
            return result;
        }

        /**
         * 除法[与/一致]
         *
         * @param nums
         * @return 运算结果
         */
        public static int division(int... nums) {
            int result = nums[0];
            for (int i = 1; i < nums.length; i++) {
                result /= nums[i];
            }
            return result;
        }

        /**
         * 取反[与~一致,无需+1]
         *
         * @param num
         * @return 运算结果
         */
        public static int antibs(int num) {
            return -num;
        }

        /**
         * 绝对值
         *
         * @param num
         * @return 运算结果
         */
        public static int abs(int num) {
            return num < 0 ? -num : num;
        }

        /**
         * 返回最小值
         *
         * @return 最小值
         */
        public static int min(int n1, int n2) {
            return (n1 == n2) ? n1 : (n1 < n2) ? n1 : n2;
        }

        /**
         * 返回最小值
         *
         * @param nums 整数数组
         * @return 最小值
         */
        public static int min(int... nums) {
            if (nums == null || nums.length < 1) {
                new RuntimeException("org.tango.utils.CalcUtils.Math.min(int...nums) nums is null or length lt 1!");
            }
            int min = nums[0];
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] < min) {
                    min = nums[i];
                }
            }
            return min;
        }

        /**
         * 返回最大值
         *
         * @return 最大值
         */
        public static int max(int n1, int n2) {
            return (n1 == n2) ? n1 : (n1 > n2) ? n1 : n2;
        }

        /**
         * 返回最大值
         *
         * @param nums 整数数组
         * @return 最大值
         */
        public static int max(int... nums) {
            if (nums == null || nums.length < 1) {
                new RuntimeException("org.tango.utils.CalcUtils.Math.max(int...nums) nums is null or length lt 1!");
            }
            int max = nums[0];
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] > max) {
                    max = nums[i];
                }
            }
            return max;
        }
    }

    /**
     * 计算机 @author tAngo
     */
    public static class Binary {

        public enum EightLevel {
            HEIGHTx64, MIDDLE_HEIGHTx64, MIDDLEx64, BOTTOMx64, HEIGHTx86, MIDDLE_HEIGHTx86, MIDDLEx86, BOTTOMx86
        }

        /**
         * 返回二进制
         *
         * @param <N>     数字类型
         * @param number
         * @param bitSize 位数
         * @return binaryString
         */
        public static <N> String getStringBinary(N number, int bitSize) {
            StringBuilder builder = new StringBuilder();
            StringBuilder stringBin = new StringBuilder();
            if (number instanceof Long) {
                stringBin.append(Long.toBinaryString((Long) number));
            } else if (number instanceof Integer || number instanceof Double) {
                stringBin.append(Long.toBinaryString((Integer) number));
            }
            for (int j = stringBin.length(); j < bitSize; j++) {
                stringBin.insert(0, "0");
            }
            builder.append(stringBin);
            return builder.toString();
        }

        /**
         * 返回十六进制
         *
         * @param inputNumber
         * @return hexString
         */
        public static String getHexString(Long inputNumber) {
            String hexString = "0x";
            hexString += inputNumber > 0 ? Long.toHexString(inputNumber) : Long.toHexString(java.lang.Math.abs(inputNumber));
            return hexString;
        }

        /**
         * 返回指定八位的整数
         *
         * @param <N>
         * @param number
         * @param eightLevel 八位级别
         * @return 整数
         */
        public static <N> int getEightBitByLevel(N number, EightLevel eightLevel) {
            long result = 0;
            long number16Bit = Long.parseLong(String.valueOf(number));
            switch (eightLevel) {

                case HEIGHTx64:
                    result = number16Bit >>> 56 & 0xff;
                    break;

                case MIDDLE_HEIGHTx64:
                    result = number16Bit >>> 48 & 0xff;
                    break;

                case MIDDLEx64:
                    result = number16Bit >>> 40 & 0xff;
                    break;

                case BOTTOMx64:
                    result = number16Bit >>> 32 & 0xff;
                    break;

                case HEIGHTx86:
                    result = number16Bit >>> 24 & 0xff;
                    break;

                case MIDDLE_HEIGHTx86:
                    result = number16Bit >>> 16 & 0xff;
                    break;

                case MIDDLEx86:
                    result = number16Bit >>> 8 & 0xff;
                    break;

                case BOTTOMx86:
                    result = number16Bit & 0xff;
                    break;

                default:
                    break;

            }
            return (int) result;
        }
    }

    /** 长度 @author tAngo */
    /**
     * Mc_ = '公制' ,My_ = '市制' ,Uk_ = '英制'
     */
    public enum LengthMark {
        //[ 公制 | Mc ]
        /**
         * 光年
         */
        Mc_ly(1.0570234E-16),
        /**
         * 天文
         */
        Mc_au(6.6845813E-12),
        /**
         * 千米/公里
         */
        Mc_km(0.001),
        /**
         * 米
         */
        Mc_m(1),
        /**
         * 分米
         */
        Mc_dm(10),
        /**
         * 厘米
         */
        Mc_cm(100),
        /**
         * 毫米
         */
        Mc_mm(1000),
        /**
         * 微米
         */
        Mc_um(1000000),
        /**
         * 纳米
         */
        Mc_nm(1000000000),
        /**
         * 埃
         */
        Mc_a(10000000000l),
        //[ 市制 | My ]
        /**
         * 里
         */
        My_metric(0.002),
        /**
         * 丈
         */
        My_measure(0.3),
        /**
         * 尺
         */
        My_ruler(3),
        /**
         * 寸
         */
        My_inch(30),
        /**
         * 分
         */
        My_disp(300),
        /**
         * 厘
         */
        My_cent(3000),
        /**
         * 毫
         */
        My_least(30000),
        //[ 英制 | Uk ]
        /**
         * 海里
         */
        Uk_nmi(0.0005399568),
        /**
         * 英里
         */
        Uk_mi(0.0006213712),
        /**
         * 弗隆
         */
        Uk_fg(0.0049709695),
        /**
         * 英寻
         */
        Uk_ym(0.5468066492),
        /**
         * 码
         */
        Uk_yd(1.0936132983),
        /**
         * 英尺
         */
        Uk_ft(3.280839895),
        /**
         * 英寸
         */
        Uk_in(39.3700787402);

        private double v = 0;

        private LengthMark(double v) {
            this.v = v;
        }

        public double getValue(double length, LengthMark lengthMark) {
            return lengthMark.v * length / this.v;
        }
    }

    /**
     * 面积
     */
    public enum AreaMark {
        //[ 公制 | Mc ]
        /**
         * 平方千米
         */
        Mc_km2(0.000001),
        /**
         * 公顷
         */
        Mc_ha2(0.0001),
        /**
         * 公亩
         */
        Mc_are2(0.01),
        /**
         * 平方米
         */
        Mc_m2(1),
        /**
         * 平方分米
         */
        Mc_dm2(100),
        /**
         * 平方厘米
         */
        Mc_cm2(10000),
        /**
         * 平方毫米
         */
        Mc_mm2(1000000),
        //[ 英制 | Uk ]
        /**
         * 平方英里
         */
        Uk_mi2(3.861E-7),
        /**
         * 英亩
         */
        Uk_ac2(0.0002471054),
        /**
         * 平方竿
         */
        Uk_rd2(0.039536861),
        /**
         * 平方码
         */
        Uk_yd2(1.1959900463),
        /**
         * 平方英尺
         */
        Uk_ft2(10.7639104167),
        /**
         * 平方英寸
         */
        Uk_in2(1550.0031),
        //[ 市制 | My ]
        /**
         * 顷
         */
        My_ares2(0.000015),
        /**
         * 亩
         */
        My_mu2(0.0015),
        /**
         * 平方尺
         */
        My_ruler2(9),
        /**
         * 平方寸
         */
        My_inch2(900);

        private double v = 0;

        private AreaMark(double v) {
            this.v = v;
        }

        public double getValue(double size, AreaMark areaMark) {
            return areaMark.v * size / this.v;
        }
    }

    /**
     * 体积
     */
    public enum BulkMark {
        //[ 公制 | Mc ]
        /**
         * 立方米(m3)
         */
        Mc_m3(1),
        /**
         * 公石/百升(hL)
         */
        Mc_hL(10),
        /**
         * 升(L)
         */
        Mc_L(10),
        /**
         * 立方分米(dm)
         */
        Mc_dm3(1000),
        /**
         * 分升(dL）
         */
        Mc_dL(10000),
        /**
         * 厘升(cL）
         */
        Mc_cL(100000),
        /**
         * 毫升(mL）
         */
        Mc_mL(1000000),
        /**
         * 立方厘米(cm3）
         */
        Mc_cm3(1000000),
        /**
         * 立方毫米(mm3)
         */
        Mc_mm3(1000000000),
        //[ 英制 | Uk ]
        /**
         * 立方码(yd3)
         */
        Uk_yd3(1.3079527714),
        /**
         * 立方英尺(ft3)
         */
        Uk_ft3(35.3147248277),
        /**
         * 立方英寸(in3)
         */
        Uk_in3(61023.8445022),
        /**
         * 英加仑(UK gal）
         */
        UK_gal(219.969157332),
        /**
         * 美加仑(US gal）
         */
        US_gal(264.172052358),
        /**
         * 亩英尺
         */
        Uk_muinch(0.0008107132),
        /**
         * 桶(bbl）
         */
        Uk_bbl(6.2893081761),
        /**
         * 杯(c）
         */
        Uk_c(4226.7527752),
        /**
         * 液盎司(fl oz）
         */
        Uk_fl_oz(33814.0222016),
        /**
         * 茶匙(tsp）
         */
        Uk_tsp(67628.0444032);

        private double v = 0;

        private BulkMark(double v) {
            this.v = v;
        }

        public double getValue(double size, BulkMark bulkMark) {
            return bulkMark.v * size / this.v;
        }
    }

    /**
     * 重量
     */
    public enum WeightMark {
        //[ 公制 | Mc ]
        /**
         * 吨(ton）
         */
        Mc_ton(0.001),
        /**
         * 公担(q.）
         */
        Mc_qdot(0.01),
        /**
         * 千克(kg）
         */
        Mc_kg(1),
        /**
         * 克(g）
         */
        Mc_g(1000),
        /**
         * 毫克(mg）
         */
        Mc_mg(1000000),
        /**
         * 微克(ug）
         */
        Mc_ug(1000000000),
        //[ 英制 | Uk ]
        /**
         * 磅(lb)
         */
        Uk_lb(2.2046226218),
        /**
         * 盎司(oz)
         */
        Uk_oz(35.2739619496),
        /**
         * 克拉(ct)
         */
        Uk_ct(5000),
        /**
         * 格令(gr)
         */
        Uk_gr(15432.3583529),
        /**
         * 长吨(lt)
         */
        Uk_lt(0.0009842065),
        /**
         * 短吨(st)
         */
        Uk_st(0.0011023113),
        /**
         * 英担(lh)
         */
        Uk_lh(0.0196841306),
        /**
         * 美担(sh)
         */
        Uk_sh(0.0220462262),
        /**
         * 英石(zircon)
         */
        Uk_zircon(0.1574730444),
        /**
         * 打兰(dr)
         */
        Uk_dr(564.383391193),
        //[ 市制 | My ]
        /**
         * 担(dan)
         */
        My_dan(0.02),
        /**
         * 斤(weight)
         */
        My_weight(2),
        /**
         * 两(tael)
         */
        My_tael(20),
        /**
         * 钱(cash)
         */
        My_cash(200);

        private double v = 0;

        private WeightMark(double v) {
            this.v = v;
        }

        public double getValue(double size, WeightMark weightMark) {
            return weightMark.v * size / this.v;
        }
    }

    /**
     * 时间
     */
    public enum DateMark {
        /**
         * 年
         */
        yr(3.17E-8),
        /**
         * 周
         */
        week(0.0000016534),
        /**
         * 天
         */
        d(0.0000115741),
        /**
         * 时
         */
        h(0.0002777778),
        /**
         * 分
         */
        min(0.0166666667),
        /**
         * 秒
         */
        s(1),
        /**
         * 毫秒
         */
        ms(1000),
        /**
         * 微秒
         */
        us(1000000),
        /**
         * 纳秒
         */
        ns(1000000000),
        /**
         * 皮秒
         */
        ps(1000000000000l),
        /**
         * 飞秒
         */
        fs(1.0000000E+15);

        private double v = 0;

        private DateMark(double v) {
            this.v = v;
        }

        public double getValue(double size, DateMark dateMark) {
            return dateMark.v * size / this.v;
        }
    }

    /**
     * 温度
     */
    public enum TemperMark {
        /**
         * 摄氏度(°C)
         */
        C(1),
        /**
         * 华氏度(°F)
         */
        F(33.8),
        /**
         * 开尔文(°K)
         */
        K(274.15),
        /**
         * 兰氏度(°Ra)
         */
        Ra(493.47),
        /**
         * 列氏度°Re
         */
        Re(0.8);

        private double v = 0;

        private TemperMark(double v) {
            this.v = v;
        }

        public double getValue(double size, TemperMark temperMark) {
            return temperMark.v * size / this.v;
        }

    }

    /**
     * 压力
     */
    public enum DrangMark {
        /**
         * 千帕(kPa)
         */
        kPa(0.001),
        /**
         * 帕(Pa)
         */
        Pa(1),
        /**
         * 巴(B)
         */
        B(0.00001),
        /**
         * 毫巴(mB)
         */
        mB(0.01),
        /**
         * 托(torr)
         */
        torr(0.0075006168),
        /**
         * 标准大气压(ATM)
         */
        ATM(0.0000098692),
        /**
         * 毫米汞柱(mmHg)
         */
        mmHg(0.0075006168),
        /**
         * 英寸汞柱(inHg)
         */
        inHg(0.0002952999),
        /**
         * 毫米水柱(mmH2O)
         */
        mmH2O(0.101972),
        /**
         * 英寸水柱(inH2O)
         */
        inH2O(0.0040146457),
        /**
         * 磅力/平方英寸(psi)
         */
        psi(0.0001450377),
        /**
         * 磅力/平方英尺(psf)
         */
        psf(0.0208854342),
        /**
         * 公斤力/平方厘米
         */
        gcm(0.0000101972),
        /**
         * 公斤力/平方米
         */
        gm(0.1019716213);

        private double v = 0;

        private DrangMark(double v) {
            this.v = v;
        }

        public double getValue(double size, DrangMark drangMark) {
            return drangMark.v * size / this.v;
        }
    }

    /**
     * 功率
     */
    public enum PowerMark {
        /**
         * 兆瓦(MW)
         */
        MW(0.000001),
        /**
         * 千瓦(kW)
         */
        kW(0.001),
        /**
         * 瓦(W)
         */
        W(1),
        /**
         * 毫瓦(mW)
         */
        mW(1000),
        /**
         * 英制马力(BHP)
         */
        BHP(0.0013410221),
        /**
         * 米制马力(MHP)
         */
        MHP(0.0013596216),
        /**
         * 公斤.米/秒
         */
        gms(0.1019716213),
        /**
         * 千卡/秒
         */
        kks(0.000239),
        /**
         * 英热单位/秒
         */
        ws(0.0009478171),
        /**
         * 英尺.磅/秒
         */
        inbs(0.7375621489);

        private double v = 0;

        private PowerMark(double v) {
            this.v = v;
        }

        public double getValue(double size, PowerMark powerMark) {
            return powerMark.v * size / this.v;
        }
    }

    /**
     * 速度
     */
    public enum SpeedMark {
        /**
         * 光速(c)
         */
        c(3.3E-9),
        /**
         * 千米/秒(km/s)
         */
        kms(0.001),
        /**
         * 马赫(mach)
         */
        mach(0.0029385836),
        /**
         * 米/秒(m/s)
         */
        ms(1),
        /**
         * 海里/时(kn)
         */
        kn(1.9438444924),
        /**
         * 英里/时(mi/h)
         */
        mih(2.2369362921),
        /**
         * 英尺/秒(ft/s)
         */
        fts(3.280839895),
        /**
         * 千米/时(km/h)
         */
        kmh(3.6),
        /**
         * 英寸/秒(in/s)
         */
        ins(39.3700787402);

        private double v = 0;

        private SpeedMark(double v) {
            this.v = v;
        }

        public double getValue(double size, SpeedMark speedMark) {
            return speedMark.v * size / this.v;
        }
    }

    /**
     * 存储
     */
    public enum MemoryMark {
        /**
         * 艾字节(EB)
         */
        EB(8.67361738E-19),
        /**
         * 拍字节(PB)
         */
        PB(8.8817842E-16),
        /**
         * 太字节(TB)
         */
        TB(9.09494701E-13),
        /**
         * 千兆字节(GB)
         */
        GB(9.31322574E-10),
        /**
         * 兆字节(MB)
         */
        MB(9.536743164E-7),
        /**
         * 千字节(KB)
         */
        KB(0.0009765625),
        /**
         * 字节(B)
         */
        B(1),
        /**
         * 比特位(b)
         */
        b(8);

        private double v = 0;

        private MemoryMark(double v) {
            this.v = v;
        }

        public double getValue(double size, MemoryMark memoryMark) {
            return memoryMark.v * size / this.v;
        }
    }
}
