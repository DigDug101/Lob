/*
 * Copyright (c) 2014, David Forsythe
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  Neither the name of Lob nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.dforsyth.android.lob.queues;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

/**
 * A simple LobQueue
 */
public class SimpleQueue implements LobQueue {
    private static String TAG = SimpleQueue.class.getSimpleName();

    private HttpStack mHttpStack;
    private RequestQueue mQueue;

    public SimpleQueue() {
        mHttpStack = null;
    }

    public SimpleQueue(HttpStack stack) {
        mHttpStack = stack;
    }

    @Override
    public void prepare(Context context) {
        if (mQueue != null) {
            Log.d(TAG, "mQueue != null");
            return;
        }

        if (mHttpStack != null) {
            mQueue = Volley.newRequestQueue(context, mHttpStack);
        } else {
            mQueue = Volley.newRequestQueue(context);
        }
        mQueue.start();
    }

    @Override
    public void submitRequest(Request<?> request) {
        mQueue.add(request);
    }

    @Override
    public void cancel(Object tag) {
        mQueue.cancelAll(tag);
    }

    @Override
    public RequestQueue getRequestQueue() {
        return mQueue;
    }
}
