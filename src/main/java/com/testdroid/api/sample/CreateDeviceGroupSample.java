package com.testdroid.api.sample;

import com.testdroid.api.*;
import com.testdroid.api.dto.Context;
import com.testdroid.api.dto.Operand;
import com.testdroid.api.filter.StringFilterEntry;
import com.testdroid.api.model.APIDevice;
import com.testdroid.api.model.APIDeviceGroup;
import com.testdroid.api.model.APIUser;
import com.testdroid.api.sample.util.Common;

/**
 * @author Sławomir Pawluk <slawomir.pawluk@bitbar.com>
 */
public class CreateDeviceGroupSample {

    public static final APIClient CLIENT = Common.createApiClient();

    public static void main(String[] args) {
        try {
            // Get authenticated user
            APIUser me = CLIENT.me();

            // Create device group
            APIDeviceGroup deviceGroup = me.createDeviceGroup("My device group", APIDevice.OsType.ANDROID);

            System.out.println(String.format("Device group name: %s\nOwner id: %s\nIs group public?: %s",
                    deviceGroup.getDisplayName(), deviceGroup.getUserId(), deviceGroup.isPublic()));

            // Get devices list
            APIList<APIDevice> devicesList = CLIENT.getDevices(new Context<>(APIDevice.class)
                    .addFilter(new StringFilterEntry("osType", Operand.EQ, APIDevice.OsType.ANDROID.name())))
                    .getEntity();
            System.out.println(String.format("Got %s devices", devicesList.getLimit()));

            int i = 0;
            for (APIDevice device : devicesList.getData()) {
                if (i++ % 2 == 0) {
                    deviceGroup.addDevice(device);
                }
            }

            // Refresh device group to fetch changes from server
            deviceGroup.refresh();

            System.out.println(String.format("Device group have %s devices. Single run cost %s credits. Devices are:",
                    deviceGroup.getDeviceCount(), deviceGroup.getCreditsPrice()));

            for (APIDevice device : deviceGroup.getIncludedDevicesResource().getEntity().getData()) {
                System.out.println(device.getDisplayName());
            }

            // You can also search devices inside device group
            String deviceName = deviceGroup.getIncludedDevicesResource().getEntity().get(0).getDisplayName();
            System.out.println(String.format("Searching device with name %s...", deviceName));
            System.out.println("Results:");

            for (APIDevice device : deviceGroup.getIncludedDevicesResource(new Context<>(APIDevice.class)
                    .setLimit(10).setSearch(deviceName)).getEntity().getData()) {
                System.out.println(device.getDisplayName());
            }

            // Now we create device group for samsungs only
            APIList<APIDevice> samsungDevices = CLIENT.getDevices(new Context<>(APIDevice.class).setSearch("Samsung"))
                    .getEntity();
            APIDeviceGroup samsungsDeviceGroup = me.createDeviceGroup("Samsungs only", APIDevice.OsType.ANDROID);

            for (APIDevice device : samsungDevices.getData()) {
                samsungsDeviceGroup.addDevice(device);
            }

            samsungsDeviceGroup.refresh();

            System.out.println(String.format("Devices added to %s group", samsungsDeviceGroup.getDisplayName()));
            for (APIDevice device : samsungsDeviceGroup.getIncludedDevicesResource().getEntity().getData()) {
                System.out.println(device.getDisplayName());
            }
        } catch (APIException apie) {
            System.err.println(apie.getMessage());
        }
    }

}
