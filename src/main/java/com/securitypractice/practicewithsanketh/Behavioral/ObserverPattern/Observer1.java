package com.securitypractice.practicewithsanketh.Behavioral.ObserverPattern;
/*In the Observer Design Pattern, the Observer is the one that reacts to
the changes (like a subscriber reacting to a new video). To make this pattern
work, we create an interface called Subscriber. The job of this interface is
to define what methods a subscriber (observer) should have. In our case,
the update() method is the
one we use to notify a subscriber when something happens (like a new video).*/

import java.util.ArrayList;
import java.util.List;

interface Subscriber1{
 void notify(String video);
}
class YoutubeSubscriber1 implements Subscriber1{
     private String name;
     public YoutubeSubscriber1(String name){
         this.name = name;
     }
     @Override
     public void notify(String video){
         // When notified, this method will execute, and the subscriber watches the
         // new video
         System.out.println("Notifying "+name+" of "+video);
     }

 }
 class EmailNotification implements Subscriber1{
     public String email;
     public EmailNotification(String email){
         this.email = email;
    }
    @Override
    public void notify(String video){
        System.out.println("sending email to" + email + "upload new vide"+ video);
    }
  }
  class PushNotification implements Subscriber1{
      public String phone;
      public PushNotification(String phone){
          this.phone = phone;
      }
      @Override
      public void notify(String video){
          System.out.println("sending push notification to" + phone + "upload new vide"+ video);
      }
  }
  /*
  The Subject is the one that changes. In our case, it’s the YouTubeChannel
   (the channel posting videos). The YouTubeChannel needs to keep track of all its
  subscribers and notify them when something changes (like uploading a new video).
  */
 interface YoutubeChannel1{
    public void subscribe(Subscriber1 subscriber);
    public void unsubscribe(Subscriber1 subscriber);
    public void notifySubscribers(String video);
}

    class YoutubeChannel1Impl implements YoutubeChannel1{
     private List<Subscriber1> subscriber1Lists = new ArrayList<>();
     private String video;
        @Override
        public void subscribe(Subscriber1 subscriber) {
            subscriber1Lists.add(subscriber);
        }

        @Override
        public void unsubscribe(Subscriber1 subscriber) {
            subscriber1Lists.remove(subscriber);
        }

        @Override
        public void notifySubscribers(String video){
            this.video = video;
            for(Subscriber1 subscriber1 : subscriber1Lists){
                subscriber1.notify(video);
            }
        }
        public void uploadNewVideo(String video){
            this.video = video;
            notifySubscribers(video);
        }
    }


public class Observer1 {
    public static void main(String[] args) {
        YoutubeChannel1Impl youtubeChannel1 = new YoutubeChannel1Impl();
        YoutubeSubscriber1 aryan = new YoutubeSubscriber1("Aryan");
        YoutubeSubscriber1 youtubeSubscriber1 = new YoutubeSubscriber1("Sanketh");
        youtubeChannel1.subscribe(aryan);
        youtubeChannel1.subscribe(youtubeSubscriber1);

        youtubeChannel1.uploadNewVideo("New Video doremon");

        youtubeChannel1.unsubscribe(aryan);
        youtubeChannel1.uploadNewVideo("Nobita");


    }
}

