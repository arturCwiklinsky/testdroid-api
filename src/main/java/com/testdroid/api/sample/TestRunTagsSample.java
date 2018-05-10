package com.testdroid.api.sample;

import com.testdroid.api.APIClient;
import com.testdroid.api.APIException;
import com.testdroid.api.APIListResource;
import com.testdroid.api.dto.Context;
import com.testdroid.api.model.*;
import com.testdroid.api.sample.util.Common;

import java.io.File;

/**
 * @author Sławomir Pawluk <slawomir.pawluk@bitbar.com>
 */
public class TestRunTagsSample {

    private static final APIClient CLIENT = Common.createApiClient();

    public static void main(String[] args) {
        try {
            // Get authorized user
            APIUser me = CLIENT.me();

            // Create project
            APIProject project = me.createProject(APIProject.Type.ANDROID);

            // Get test run config
            APITestRunConfig config = project.getTestRunConfig();

            // Configure testrun
            config.setAppCrawlerRun(Boolean.TRUE);

            config.update();

            project.uploadApplication(new File(Common.ANDROID_APPLICATION_RESOURCE_PATH), Common
                    .ANDROID_FILE_MIME_TYPE);

            APITestRun testRun = project.run();

            String tagName = "Test run tag";
            APITag tag = testRun.addTag(tagName);

            System.out.println(String.format("Created tag with name: %s", tag.getName()));

            testRun.refresh();

            // List test run tags
            System.out.println("Test run have now tags:");
            for (APITag t : testRun.getTagsResource().getEntity().getData()) {
                System.out.println(String.format("\t%s", t.getName()));
            }

            // Project can be found using tag name
            APIListResource<APITestRun> projects = project.getTestRunsResource(new Context(APITestRun.class)
                    .setSearch(tagName));

            System.out.println(String.format("Projects found using tag name: %s", tagName));
            for (APITestRun tr : projects.getEntity().getData()) {
                System.out.println(String.format("\t%s", tr.getDisplayName()));
            }

        } catch (APIException apie) {
            System.err.println(apie.getMessage());
        }
    }

}
