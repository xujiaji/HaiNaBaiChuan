/*
 * Copyright 2018 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xujiaji.hnbc.contract.base;

import java.util.List;

import io.xujiaji.xmvp.contracts.XContract;

/**
 * Created by jiana on 16-11-20.
 */

public interface RefreshContract {

    interface Presenter extends XContract.Presenter {
        /**
         * 请求数据
         * @param nowSize 当前数据长度
         */
        void requestLoadListData(int nowSize);

        /**
         * 请求更新列表数据
         */
        void requestUpdateListData();
    }

    interface View <X> extends XContract.View {

        /**
         * 更新列表成功
         */
        void updateListSuccess(List<X> datas);

        /**
         * 更新失败
         */
        void updateListFail(String err);

        /**
         * 加载数据成功
         */
        void loadListDataSuccess(List<X> datas);

        /**
         * 加载数据失败
         * @param err
         */
        void loadListDataFail(String err);

        /**
         * 数据已经被加载完
         */
        void loadListDateOver();

    }
}
