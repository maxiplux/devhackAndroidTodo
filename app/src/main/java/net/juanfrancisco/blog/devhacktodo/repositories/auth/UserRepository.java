package net.juanfrancisco.blog.devhacktodo.repositories.auth;

import net.juanfrancisco.blog.devhacktodo.domain.models.User;
import net.juanfrancisco.blog.devhacktodo.helpers.CallBack;

/**
 * Created by franc on 2/12/2017.
 */

public interface UserRepository {

    void login(String email, String password, CallBack<User> callback);

    void signUp(User user, CallBack<User> callback);

}
