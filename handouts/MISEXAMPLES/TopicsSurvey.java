import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TopicsSurvey {
  public static void main(String[] args) {
    String filename = "reponses.txt";
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String response = null;
      int numOfResponses = 0;
      Map<String, Integer> freqMap = new TreeMap<>();
      while ((response = br.readLine()) != null) {
        numOfResponses++;
        String[] responseTopics = response.split(", ");
        for(String topic : responseTopics) {
          Integer count = freqMap.get(topic);
          if (count == null) {
            count = 0;
          }
          freqMap.put(topic, ++count);
        }
      }
      System.out.println(numOfResponses);
      System.out.println();
      System.out.println(freqMap);
      System.out.println();

      //_______Using for(:) loop___________________
      for (String topic : freqMap.keySet()) {
          System.out.println(topic + ": " + freqMap.get(topic));
      }
      System.out.println();

      //________MultiMap__________________________
      Map<Integer, List<String>> multiMap = new TreeMap<>();
      for (String topic : freqMap.keySet()) {
        Integer count = freqMap.get(topic);
        List<String> topicList = multiMap.get(count);
        if (topicList == null) {
          topicList = new ArrayList<>();
          multiMap.put(count, topicList);
        }
        topicList.add(topic);
      }
      for (Integer count : multiMap.keySet()) {
        List<String> topics = multiMap.get(count);
        System.out.println(count + ": " + topics);
      }
      System.out.println();

      //_______Using stream________________________
      freqMap.keySet().stream().sorted()
             .forEach(topic -> System.out.println(topic + ": " + freqMap.get(topic)));
      System.out.println();

      //___________________________________________
      Map<String, Integer> sortedByCount = freqMap.entrySet()
          .stream()
          .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
      System.out.println(sortedByCount);
      System.out.println();
      sortedByCount.keySet().stream()
                   .forEach(topic -> System.out.println(topic + ": " + sortedByCount.get(topic)));

    } catch (FileNotFoundException fnf) {
      fnf.printStackTrace();
      return;
    } catch (IOException ioe) {
      ioe.printStackTrace();
      return;
    }
  }
}