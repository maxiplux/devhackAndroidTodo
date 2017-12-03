package net.juanfrancisco.blog.devhacktodo.helpers;

/**
 * Created by franc on 2/12/2017.
 */

public interface CallBack<T>  {
    void success(T result);

    void error(Exception error);
}

