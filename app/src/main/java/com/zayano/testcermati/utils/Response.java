package com.zayano.testcermati.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Response {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ERROR, LOADING, SUCCESS, UPDATE})
    @interface ResponseStatusDef {
    }

    public static final int ERROR = 0;
    public static final int LOADING = 1;
    public static final int SUCCESS = 2;
    public static final int UPDATE = 3;

    public final int status;

    @Nullable
    public final Object data;

    @Nullable
    public final Throwable error;

    public Response(@ResponseStatusDef int status, @Nullable Object data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response loading() {
        return new Response(LOADING, null, null);
    }

    public static Response success(@NonNull Object data) {
        return new Response(SUCCESS, data, null);
    }

    public static Response update(@NonNull Object data) {
        return new Response(UPDATE, data, null);
    }

    public static Response error(@NonNull Throwable error) {
        return new Response(ERROR, null, error);
    }

}
