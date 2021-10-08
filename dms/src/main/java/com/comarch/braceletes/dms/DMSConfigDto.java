package com.comarch.braceletes.dms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DMSConfigDto {
    String iotpProject;

    String iotpChannelId;

    String iotpTenant;

    String mqttPasswd;

    String mqttUrl;

    String mqttUser;
    
    
    
    ///
    String dMSToken;

    String imei;

    String dmsDeviceToken;

    String appVersionName;

}
