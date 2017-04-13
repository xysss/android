package com.util;

public class BluetoothTools{
    public static final String ACTION_DATA_TO_APP = "ACTION_DATA_TO_APP";
    public static final String ACTION_CONNECT_SUCCESS = "ACTION_CONNECT_SUCCESS";
    public static final String ACTION_CONNECT_DISCONNECT = "ACTION_CONNECT_DISCONNECT";
    public static final String ACTION_CONNECT_ERROR = "ACTION_CONNECT_ERROR";
    public static final String ACTION_START_DISCOVERY = "ACTION_START_DISCOVERY";

    public static final int MESSAGE_READ_OBJECT = 0x00000001;
    public static final int MESSAGE_CONNECT_SUCCESS = 0x00000002;
    public static final int MESSAGE_CONNECT_ERROR = 0x00000003;

    public static final int MESSAGE_WANGLUOWENTI = 0x00000003;
    public static final int MESSAGE_DENGLUCHENGGONG = 0x00000001;
    public static final int MESSAGE_DENGLUSIBAI = 0x00000002;

    public static final int MESSAGE_ZHUCECHENGGONG = 0x00000001;
    public static final int MESSAGE_ZHUCESIBAI = 0x00000002;

    /**
     * 字符串常量，Intent中的数据
     */
    public static final String DATA = "DATA";

    /**
     * Action类型标识符，Action类型为 未发现设备
     */
    public static final String ACTION_NOT_FOUND_SERVER = "ACTION_NOT_FOUND_DEVICE";

    /**
     * Action：设备列表
     */
    public static final String ACTION_FOUND_DEVICE = "ACTION_FOUND_DEVICE";

    /**
     * Action：选择的用于连接的设备
     */
    public static final String ACTION_SELECTED_DEVICE = "ACTION_SELECTED_DEVICE";

    /**
     * Action：开启服务器
     */
    public static final String ACTION_START_SERVER = "ACTION_STARRT_SERVER";

    /**
     * Action：关闭后台Service
     */
    public static final String ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";

    /**
     * Action：到Service的数据
     */
    public static final String ACTION_DATA_TO_SERVICE = "ACTION_DATA_TO_SERVICE";

}
