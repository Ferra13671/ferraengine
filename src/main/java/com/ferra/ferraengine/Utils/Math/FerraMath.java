package com.ferra.ferraengine.Utils.Math;

import com.ferra.ferraengine.Utils.Annotations.Experimental;

/**
 * A utility for performing various mathematical calculations with almost minimal inaccuracy.
 * Unlike the Math class in java, all methods return float numbers instead of double.
 *
 * @author Ferra13671
 */

public class FerraMath {

    public static final float E = 2.7182818284590452354f;

    public static final float PI = 3.14159265358979323846f;
    public static final float Half_PI = PI * 0.5f;
    public static final float PI2 = PI * 2.0f;
    public static final float PI_INV = 1.0f / PI;

    public static final float DEGREES_TO_RADIANS = 0.017453292519943295f;

    public static final float RADIANS_TO_DEGREES = 57.29577951308232f;

    public static final float INFINITY = Float.POSITIVE_INFINITY;


    /**
     * Convects angles to radians.
     *
     * @param deg   The degrees to be converted.
     * @return   Radians.
     */
    public static float degreesToRadians(float deg) {
        return deg * DEGREES_TO_RADIANS;
    }


    /**
     * Converts radians to degrees
     *
     * @param rad   The radians to be converted.
     * @return   Degrees.
     */
    public static float radiansToDegrees(float rad) {
        return rad * RADIANS_TO_DEGREES;
    }



    /**
     * Calculates the sine of the entered radians.
     *
     * @param rad   Radians for which the sine must be found.
     * @return   Sine of radians.
     */
    public static float sin_rad(float rad) {
        double i  = java.lang.Math.rint(rad * PI_INV);
        float x  = rad - (float)(i * PI);
        float qs = 1-2*((int)i & 1);
        float x2 = x*x;
        double r;
        x = qs*x;
        r = k7;
        r = r*x2 + k6;
        r = r*x2 + k5;
        r = r*x2 + k4;
        r = r*x2 + k3;
        r = r*x2 + k2;
        r = r*x2 + k1;
        return (float)(x + x*x2*r);
    }


    /**
     * Calculates the sine of the entered degrees.
     *
     * @param deg   Degrees for which the sine must be found.
     * @return   Sine of degrees.
     */
    public static float sin_deg(float deg) {
        return sin_rad(degreesToRadians(deg));
    }


    /**
     * Calculates the cosine of the entered radians.
     *
     * @param rad   Radians for which the cosine must be found.
     * @return   Cosine of radians.
     */
    public static float cos_rad(float rad) {
        return sin_rad(rad + Half_PI);
    }


    /**
     * Calculates the cosine of the entered degrees.
     *
     * @param deg   Degrees for which the cosine must be found.
     * @return   Cosine of degrees.
     */
    public static float cos_deg(float deg) {
        return cos_rad(degreesToRadians(deg));
    }


    /**
     * Converts rectangular coordinates (x, y) to polar coordinates (r, theta) and returns theta.
     * <p>
     * <a href="https://math.stackexchange.com/questions/1098487/atan2-faster-approximation/1105038#answer-1105038">Original code</a>
     *
     * @param y   x coordinate.
     * @param x   y coordinate
     * @return Theta from polar coordinates (r, theta).
     */
    public static float atan2(float y, float x) {
        float ax = x >= 0.0f ? x : -x,
                ay = y >= 0.0f ? y : -y;
        float a = min(ax, ay) / max(ax, ay);
        float s = a * a;
        double r = ((-0.0464964749 * s + 0.15931422) * s - 0.327622764) * s * a + a;
        if (ay > ax)
            r = 1.57079637 - r;
        if (x < 0.0)
            r = 3.14159274 - r;
        return (float)(y >= 0 ? r : -r);
    }


    /**
     * Returns the product of the first two arguments added with the third argument.
     *
     * @param a   a value.
     * @param b   a value.
     * @param c   a value.
     * @return   a * b + c.
     */
    public static float fma(float a, float b, float c) {
        return a * b + c;
    }


    /**
     * Rounds the argument to the nearest whole number to the smaller side.
     *
     * @param value   The argument to be rounded.
     * @return   A rounded argument.
     */
    public static int floor(float value) {
        int a = (int)value;
        return value < (float)a ? a - 1 : a;
    }

    /**
     * Rounds the argument to the nearest whole number upwards.
     *
     * @param value   The argument to be rounded.
     * @return   A rounded argument.
     */
    public static int ceil(float value) {
        int a = (int)value;
        return value > (float)a ? a + 1 : a;
    }


    /**
     * Rounds the argument to the nearest even number.
     * If the argument is equally close to two integers, the method will favor the larger number.
     *
     * @param value   The argument to be rounded.
     * @return   A rounded argument.
     */
    public static int round(float value) {
        float a = value - (int) value;
        return a < 0.5f ? (int) value : (int) value + 1;
    }


    /**
     * Returns the absolute value of the argument. If the argument is non-negative, returns its value.
     * If the argument is negative, its negation is returned.
     *
     * @param value   The argument whose absolute value is to be determined.
     * @return   The absolute value of the argument.
     */
    public static float abs(float value) {
        return value >= 0.0F ? value : -value;
    }


    /**
     * Returns f × 2^scaleFactor
     *
     * @param f   number to be scaled by a power of two.
     * @param scaleFactor   power of 2 used to scale {@code f}
     * @return   f × 2^scaleFactor
     */
    public static float scalb(float f, int scaleFactor) {
        return f * simplePow(2, scaleFactor);
    }


    /**
     * A simpler and more trivial way of raising a number to the right degree.
     * Unlike the other methods, you can only raise a number to an integer degree.
     *
     * @param number   The number to be raised to a degree.
     * @param degree   Necessary degree.
     * @return   A number raised to a degree.
     */
    public static float simplePow(float number, int degree) {
        if (degree == 0) return 1;
        if (degree == 1) return number;

        if (degree < 0) return 1 / simplePow(number, -degree);
        float a = number;

        for (int i = 1; i < degree; i++) {
            a = a * number;
        }
        return a;
    }


    /**
     * Raises a number to the desired degree.

     * Copied and modified from the {@code compute(final double x, final double y)} method
     * from the {@code FdLibm.Pow} class.
     *
     * @param number   The number to be raised to a degree.
     * @param degree   Necessary degree.
     * @return   A number raised to a degree.
     *
     * @see FdLibm.Pow
     */
    @Experimental("I don't know if this method will be slightly faster than the unmodified method since testing is still being done.")
    public static float pow(float number, float degree) {
        double z;
        double r, s, t, u, v, w;
        int i, j, k, n;

        if (degree == 0.0)
            return 1.0f;

        if (Double.isNaN(number) || Double.isNaN(degree))
            return number + degree;

        final float y_abs = abs(degree);
        double x_abs   = abs(number);
        if (degree == 2.0) {
            return number * number;

        } else if (y_abs == 1.0) {
            return (degree == 1.0) ? number : 1.0f / number;
        } else if (y_abs == INFINITY) {
            if (x_abs == 1.0)
                return 0.0f;
            else if (x_abs > 1.0)
                return (degree >= 0) ? degree : 0.0f;
            else
                return (degree < 0) ? -degree : 0.0f;
        }

        final int hx = __HI(number);
        int ix = hx & 2147483647;
        int y_is_int  = 0;
        if (hx < 0) {
            if (y_abs >= 9.007199254740992E15)
                y_is_int = 2;
            else if (y_abs >= 1.0) {
                long y_abs_as_long = (long) y_abs;
                if ( ((double) y_abs_as_long) == y_abs) {
                    y_is_int = 2 -  (int)(y_abs_as_long & 0x1L);
                }
            }
        }

        if (x_abs == 0.0 ||
                x_abs == INFINITY ||
                x_abs == 1.0) {
            z = x_abs;
            if (degree < 0.0)
                z = 1.0/z;
            if (hx < 0) {
                if (((ix - 1072693248) | y_is_int) == 0) {
                    z = 1.0;
                } else if (y_is_int == 1)
                    z = -1.0 * z;
            }
            return (float) z;
        }

        n = (hx >> 31) + 1;

        if ((n | y_is_int) == 0)
            return 1.0f;

        s = 1.0;
        if ( (n | (y_is_int - 1)) == 0)
            s = -1.0;

        double p_h, p_l, t1, t2;
        if (y_abs > 0x1.00000_ffff_ffffp31) {
            final double INV_LN2  = 0x1.7154_7652_b82fep0;
            final double INV_LN2_H = 0x1.715476p0;
            final double INV_LN2_L = 0x1.4ae0_bf85_ddf44p-26;

            if (x_abs < 0x1.fffff_0000_0000p-1)
                return (float)(degree < 0.0 ? INFINITY : s * 0.0);
            if (x_abs > 0x1.00000_ffff_ffffp0)
                return (float)(degree > 0.0 ? INFINITY : s * 0.0);

            t = x_abs - 1.0;
            w = (t * t) * (0.5 - t * (0.3333333333333333333333 - t * 0.25));
            u = INV_LN2_H * t;
            v =  t * INV_LN2_L - w * INV_LN2;
            t1 = u + v;
            t1 =__LO(t1, 0);
            t2 = v - (t1 - u);
        } else {
            final double CP = 0x1.ec70_9dc3_a03fdp-1;
            final double CP_H = 0x1.ec709ep-1;
            final double CP_L = -0x1.e2fe_0145_b01f5p-28;

            double z_h, z_l, ss, s2, s_h, s_l, t_h, t_l;
            n = 0;
            if (ix < 1048576) {
                x_abs *= 0x1.0p53;
                n -= 53;
                ix = __HI(x_abs);
            }
            n  += ((ix) >> 20) - 0x3ff;
            j  = ix & 0x000fffff;
            ix = j | 0x3ff00000;
            if (j <= 0x3988E)
                k = 0;
            else if (j < 0xBB67A)
                k = 1;
            else {
                k = 0;
                n += 1;
                ix -= 0x00100000;
            }
            x_abs = __HI(x_abs, ix);
            final double[] BP = {1.0, 1.5};
            final double[] DP_H = {0.0, 0x1.2b80_34p-1};
            final double[] DP_L = {0.0, 0x1.cfde_b43c_fd006p-27};

            final double L1 = 0x1.3333_3333_33303p-1;  //  5.99999999999994648725e-01
            final double L2 = 0x1.b6db_6db6_fabffp-2;  //  4.28571428578550184252e-01
            final double L3 = 0x1.5555_5518_f264dp-2;  //  3.33333329818377432918e-01
            final double L4 = 0x1.1746_0a91_d4101p-2;  //  2.72728123808534006489e-01
            final double L5 = 0x1.d864_a93c_9db65p-3;  //  2.30660745775561754067e-01
            final double L6 = 0x1.a7e2_84a4_54eefp-3;  //  2.06975017800338417784e-01
            u = x_abs - BP[k]; // BP[0]=1.0, BP[1]=1.5
            v = 1.0 / (x_abs + BP[k]);
            ss = u * v;
            s_h = ss;
            s_h = __LO(s_h, 0);
            t_h = 0.0;
            t_h = __HI(t_h, ((ix >> 1) | 536870912) + 524288 + (k << 18) );
            t_l = x_abs - (t_h - BP[k]);
            s_l = v * ((u - s_h * t_h) - s_h * t_l);
            s2 = ss * ss;
            r = s2 * s2* (L1 + s2 * (L2 + s2 * (L3 + s2 * (L4 + s2 * (L5 + s2 * L6)))));
            r += s_l * (s_h + ss);
            s2  = s_h * s_h;
            t_h = 3.0 + s2 + r;
            t_h = __LO(t_h, 0);
            t_l = r - ((t_h - 3.0) - s2);
            u = s_h * t_h;
            v = s_l * t_h + t_l * ss;
            p_h = u + v;
            p_h = __LO(p_h, 0);
            p_l = v - (p_h - u);
            z_h = CP_H * p_h;
            z_l = CP_L * p_h + p_l * CP + DP_L[k];
            t = n;
            t1 = (((z_h + z_l) + DP_H[k]) + t);
            t1 = __LO(t1, 0);
            t2 = z_l - (((t1 - t) - DP_H[k]) - z_h);
        }

        // Split up degree into (y1 + y2) and compute (y1 + y2) * (t1 + t2)
        double y1 = __LO(degree, 0);
        p_l = (degree - y1) * t1 + degree * t2;
        p_h = y1 * t1;
        z = p_l + p_h;
        j = __HI(z);
        i = __LO(z);
        if (j >= 1083179008) {                           // z >= 1024
            if (((j - 1083179008) | i)!=0)               // if z > 1024
                return INFINITY;                     // Overflow
            else {
                final double OVT     =  8.0085662595372944372e-0017; // -(1024-log2(ovfl+.5ulp))
                if (p_l + OVT > z - p_h)
                    return INFINITY;   // Overflow
            }
        } else if ((j & 2147483647) >= 1083231232 ) {        // z <= -1075
            if (((j - -1064252416) | i)!=0)           // z < -1075
                return (float)(s * 0.0);           // Underflow
            else {
                if (p_l <= z - p_h)
                    return (float)(s * 0.0);      // Underflow
            }
        }
        final double P1      =  0x1.5555_5555_5553ep-3;  //  1.66666666666666019037e-01
        final double P2      = -0x1.6c16_c16b_ebd93p-9;  // -2.77777777770155933842e-03
        final double P3      =  0x1.1566_aaf2_5de2cp-14; //  6.61375632143793436117e-05
        final double P4      = -0x1.bbd4_1c5d_26bf1p-20; // -1.65339022054652515390e-06
        final double P5      =  0x1.6376_972b_ea4d0p-25; //  4.13813679705723846039e-08
        final double LG2     =  0x1.62e4_2fef_a39efp-1;  //  6.93147180559945286227e-01
        final double LG2_H   =  0x1.62e43p-1;            //  6.93147182464599609375e-01
        final double LG2_L   = -0x1.05c6_10ca_86c39p-29; // -1.90465429995776804525e-09
        i = j & 2147483647;
        k = (i >> 20) - 1023;
        n = 0;
        if (i > 1071644672) {
            n = j + (1048576 >> (k + 1));
            k = ((n & 2147483647) >> 20) - 0x3ff;
            t = 0.0;
            t = __HI(t, (n & ~(1048575 >> k)) );
            n = ((n & 1048575) | 1048576) >> (20 - k);
            if (j < 0)
                n = -n;
            p_h -= t;
        }
        t = p_l + p_h;
        t = __LO(t, 0);
        u = t * LG2_H;
        v = (p_l - (t - p_h)) * LG2 + t * LG2_L;
        z = u + v;
        w = v - (z - u);
        t  = z * z;
        t1  = z - t * (P1 + t * (P2 + t * (P3 + t * (P4 + t * P5))));
        r  = (z * t1) / (t1 - 2.0) - (w + z * w);
        z  = 1.0 - (r - z);
        j  = __HI(z);
        j += (n << 20);
        if ((j >> 20) <= 0)
            z = Math.scalb(z, n);
        else {
            int z_hi = __HI(z);
            z_hi += (n << 20);
            z = __HI(z, z_hi);
        }
        return (float)(s * z);
    }


    public static float min(float a, float b) {
        return a <= b ? a : b;
    }


    public static float max(float a, float b) {
        return a >= b ? a : b;
    }














    private static final double k1 = -0.1666666666666601;
    private static final double k2 = 0.008333333333277843;
    private static final double k3 = -1.9841269823583015E-4;
    private static final double k4 = 2.7557316456169086E-6;
    private static final double k5 = -2.50518726968975E-8;
    private static final double k6 = 1.60478927890934E-10;
    private static final double k7 = -7.371175496389783E-13;
    private static int __HI(double x) {
        long transducer = Double.doubleToRawLongBits(x);
        return (int)(transducer >> 32);
    }

    private static double __HI(double x, int high) {
        long transX = Double.doubleToRawLongBits(x);
        return Double.longBitsToDouble((transX & 0x0000_0000_FFFF_FFFFL) |
                ( ((long)high)) << 32 );
    }

    private static int __LO(double x) {
        long transducer = Double.doubleToRawLongBits(x);
        return (int)transducer;
    }

    private static double __LO(double x, int low) {
        long transX = Double.doubleToRawLongBits(x);
        return Double.longBitsToDouble((transX & 0xFFFF_FFFF_0000_0000L) |
                (low    & 0x0000_0000_FFFF_FFFFL));
    }
}
