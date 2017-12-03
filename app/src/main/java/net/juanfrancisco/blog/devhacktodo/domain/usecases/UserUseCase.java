package net.juanfrancisco.blog.devhacktodo.domain.usecases;

import net.juanfrancisco.blog.devhacktodo.domain.models.User;
import net.juanfrancisco.blog.devhacktodo.helpers.CallBack;


public interface UserUseCase
{
    void login(String email, String password, boolean remember, CallBack<User> callback);
    void signUp(String fullName, String email, String password, CallBack<User> callback);
}
