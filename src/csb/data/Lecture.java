/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csb.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Shi Lin Lu
 */
public class Lecture implements Comparable{
    final StringProperty topic;
    final IntegerProperty sessions;
    public static final String DEFAULT_TOPIC = "<ENTER TOPIC>";
    public static final int DEFAULT_SESSIONS = 2;    
    final ObjectProperty<LocalDate> date;
    public Lecture() {
        topic = new SimpleStringProperty(DEFAULT_TOPIC);
        sessions = new SimpleIntegerProperty(DEFAULT_SESSIONS);
        date = new SimpleObjectProperty(LocalDate.now());
    }
    
    public void reset() {
        setTopic(DEFAULT_TOPIC);
        setSessions(DEFAULT_SESSIONS);
    }
    
    public String getTopic() {
        return topic.get();
    }
    
    public void setTopic(String initTopic) {
        topic.set(initTopic);
    }
    
    public StringProperty topicProperty() {
        return topic;
    }
    
    public int getSessions() {
        return sessions.get();
    }
    
    public void setSessions(int initSessions) {
        sessions.set(initSessions);
    }
    public LocalDate getDate() {
        return (LocalDate)date.get();
    }
    
    public void setDate(LocalDate initDate) {
        date.set(initDate);
    }
    
    public IntegerProperty sessionProperty() {
        return sessions;
    }

    @Override
    public int compareTo(Object t) {
         Lecture item = (Lecture)t;
        return getDate().compareTo(item.getDate());
    }
}
