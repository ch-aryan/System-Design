package com.securitypractice.practicewithsanketh.Behavioral.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

class YoutubeChannel{
private List<String> subscribers = new ArrayList<>();
private String vidoes;

public void addSubscriber(String subscriber){
    subscribers.add(subscriber);
}

public String getVideo(){
    return this.vidoes;
}

public void notifySubscribers(){
    for(String subscriber : subscribers){
        System.out.println("Notifying " + subscriber);
        sendEmail(subscriber);//
    }
}

private void sendEmail(String subscriber){
    System.out.println("Sending email to " + subscriber);
}

public void addVideo(String video){
    this.vidoes = video;
    notifySubscribers();
}

}
class YoutubeSubscriber {
    private String name;

    public YoutubeSubscriber(String name){
        this.name = name;
    }
    public void subscribe(YoutubeChannel channel){
        channel.addSubscriber(this.name);
    }
    public void watchVideo(YoutubeChannel channel){
        System.out.println(this.name + " is watching " + channel.getVideo());
    }

}
public class Observer {

}
/*
Why Is This Approach Not Ideal? 😕
• Manual Checking:
In this approach, we manually notify each subscriber every time a new video is uploaded.
 If there are hundreds of subscribers, this becomes cumbersome.
• Not Scalable:

Adding a new notification method (e.g., email, SMS) requires modifying
the YouTubeChannel class, which leads to tight coupling and difficult maintenance.
• Hard to Extend:

If we wanted to add more observers (for example, send notifications through an app), we would
have to touch the YouTubeChannel class, breaking the open/closed principle.
 */