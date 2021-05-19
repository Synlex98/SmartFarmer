package com.fibo.smartfarmer.utils;

import android.os.Environment;

public class Constants {
    public static final String DB_NAME="SmartFarmer.db";
    public static final String SEASON_TABLE="season_table";
    public static final String STEPS_TABLE="steps_table";
    public static final String SEASON_ID="_ID";
    public static final String STEP_ID="_ID";
    public static final String NO_OF_FARMS="N_FARMS";
    public static final String FARM_SIZE="F_SIZE";
    public static final String HARVESTED_STOCK="H_STOCK";
    public static final String CURRENT_STAGE="C_STAGE";
    public static final String DELETE_QUERY="DROP TABLE IF EXISTS ";
    public static final String DEFAULT_STOCK = "Not Available";
    public static final String INITIAL_STAGE = "Farm Clearance";
    public static final String HARVESTING_STAGE="Harvesting";
    public static final String ONLINE_FILES_URL="https://themarket.co.ke/SmartFarmer/";

    public static final String STEP_STATUS = "step_status";
    public static final String STEP_NAME = "step_name";

    public static final String STEP_SEASON_ID = "_SID";
    public static final String STATUS_DONE = "done";
    public static final String STATUS_PENDING = "pending";
    public static final String CLEARANCE_STAGE = "Farm Clearance";
    public static final String PLANTING_STAGE = "Planting Stage";
    public static final String WEEDING_STAGE = "Weeding Stage";
    public static final String HISTORY_TABLE = "history_table";
    public static final String LAST_PEST_DATE = "last_pest";
    public static final String LAST_SPRAY_DATE = "last_spray";
    public static final String FILES_STORAGE_DIR= Environment.getExternalStorageDirectory()+"/SmartFarmer";
    public static final String CLEARANCE_FILE = "farm_clearance.pdf";
    public static final String PLANTING_FILE = "planting.pdf";
    public static final String WEEDING_FILE ="weeding.pdf" ;
    public static final String HARVESTING_FILE = "harvesting.pdf";
    public static final int GALLERY_REQUEST_CODE = 301;
    public static final String IMAGE_KEY = "image";
    public static final String ML_PREDICTED = "predicted";
    public static final String ML_HEALTHY_IMAGE="Healthy";
    public static final String ML_DISEASE = "disease";
    public static final String ML_ACCURACY = "accuracy";
    public static final String LABEL_PASSED = "passed";
    public static final String LABEL_FAILED = "fail";
    public static final String MESSAGE_ID = "message_id";
    public static final String FROM_ID = "from_id";
    public static final String FROM_NAME = "from_name";
    public static final String MESSAGE_BODY = "message_body";
    public static final String TIME_SENT = "time_sent";
    public static final String SUCCESS = "success";
    public static final String CHAT_TABLE = "chats_table";
    public static final String CHAT_ID = "_CHAT_ID";
    public static final String API_KEY = "key=AAAAURdoDfw:APA91bGIFiOa2eGPRLQ-KQcf5Tx3ZaeZWTxCpXzlzFVcr5zrC6rVVpTSD0kfLpHfY2R3rtg-srQBIP0PGDMeQge_yOKYBVnR6NT2V0X7Qp_jQx05j-0PXWhNew86uUfc9Ye-rjsjN6kT";
    public static final String FILE_NAME = "file_name";
    public static final String FERTILIZER_FILE = "fertilizer.json";
    public static final String NAME="name";
    public static final String QUALITY = "quality";

    public static final String DISEASE_FILE = "diseases.json";
    public static final String IS_FIRST_LAUNCH = "first_launch";

}
