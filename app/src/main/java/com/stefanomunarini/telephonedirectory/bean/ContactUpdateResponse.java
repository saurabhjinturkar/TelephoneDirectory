//package com.stefanomunarini.telephonedirectory.bean;
//
//import java.util.List;
//
///**
// * Created by Saurabh on 8/28/2015.
// */
//public class ContactUpdateResponse {
//    private long version;
//    private String data;
//
//    public long getVersion() {
//        return version;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public void setVersion(long version) {
//        this.version = version;
//    }
//
//    @Override
//    public String toString() {
//        return "ContactUpdateResponse{" +
//                "version=" + version +
//                ", data='" + data + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ContactUpdateResponse that = (ContactUpdateResponse) o;
//
//        if (version != that.version) return false;
//        return !(data != null ? !data.equals(that.data) : that.data != null);
//
//    }
//
//    @Override
//    public int hashCode() {
//        return (int) (version ^ (version >>> 32));
//    }
//}
