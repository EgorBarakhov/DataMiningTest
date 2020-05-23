package ru.kpfu.itis.barakhov;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.groups.GroupField;

import java.util.*;

public class FriendsInterests {
    private static final int MY_ID = 558137095;
    private static VkApiClient vkApiClient;
    private static UserActor userActor;
    private static final String ACCESS_TOKEN
            = "0677640667b64af0d8336b54c2936d721d938d5e0341f4995e1a5722f313b217bc327a9aeb864ef8f5de4";
    private static final int APP_ID = 7479328;

    public static void main(String[] args) throws ClientException, ApiException, InterruptedException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vkApiClient = new VkApiClient(transportClient);
        userActor = new UserActor(APP_ID, ACCESS_TOKEN);
        List<Integer> friends = vkApiClient.friends().get(userActor).userId(MY_ID).execute().getItems();
        Map<Integer, Set<String>> interestsCoincidenceForUser = getInterestsCoincidenceForUser(friends);
        Scanner scanner = new Scanner(System.in);
        int me = scanner.nextInt(), count;
        Set<String> myInterests = getGroupsInterests(getGroups(me));
        for (Map.Entry<Integer, Set<String>> entryIDSetInterests: interestsCoincidenceForUser.entrySet()) {
            count = 0;
            for (String interest : entryIDSetInterests.getValue()) {
                if (myInterests.contains(interest)) count++;
            }
            System.out.println("id " + entryIDSetInterests.getKey() + " common interests in groups: " + count);
        }
    }

    private static Map<Integer, Set<String>> getInterestsCoincidenceForUser(List<Integer> friends) throws ClientException, ApiException, InterruptedException {
        Map<Integer, Set<String>> interestsCoincidenceForUser = new HashMap<>();
        for (int friend : friends) {
            interestsCoincidenceForUser.put(friend, getGroupsInterests(getGroups(friend)));
        }
        return interestsCoincidenceForUser;
    }

    private static Set<String> getGroupsInterests(List<Integer> groups) throws InterruptedException, ClientException, ApiException {
        Set<String> interests = new HashSet<>();
        for (Integer group : groups) {
            Thread.sleep(334); //Too many requests to the server
            String activity = vkApiClient.groups().getById(userActor).groupId(String.valueOf(group)).fields(GroupField.ACTIVITY).execute().get(0).getActivity();
            if (activity == null || activity.equals("Закрытая группа") || activity.equals("Открытая группа")) continue;
            interests.add(activity);
        }
        return interests;
    }

    private static List<Integer> getGroups(int friend) throws ClientException, ApiException {
        return vkApiClient.groups().get(userActor).userId(friend).execute().getItems();
    }
}
