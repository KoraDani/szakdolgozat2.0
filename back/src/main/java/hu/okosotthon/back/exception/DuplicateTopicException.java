package hu.okosotthon.back.exception;

public class DuplicateTopicException extends RuntimeException{
    public DuplicateTopicException(String msg){
        super(msg);
    }
}
