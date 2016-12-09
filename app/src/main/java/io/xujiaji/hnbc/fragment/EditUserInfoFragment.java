/*
 * Copyright 2016 XuJiaji
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

package io.xujiaji.hnbc.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.soundcloud.android.crop.Crop;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zaaach.citypicker.CityPickerFragment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activity.MainActivity;
import io.xujiaji.hnbc.adapter.EditUserInfoAdapter;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contract.EditUserInfoContract;
import io.xujiaji.hnbc.factory.FragmentFactory;
import io.xujiaji.hnbc.fragment.base.BaseMainFragment;
import io.xujiaji.hnbc.presenter.EditUserInfoPresenter;
import io.xujiaji.hnbc.utils.BottomSheetImagePicker;
import io.xujiaji.hnbc.utils.FileUtils;
import io.xujiaji.hnbc.utils.ToastUtil;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.Bmob.getCacheDir;

/**
 * Created by jiana on 16-11-7.
 */

public class EditUserInfoFragment extends BaseMainFragment<EditUserInfoPresenter> implements EditUserInfoContract.View, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rvUserInfo)
    RecyclerView rvUserInfo;
    @BindView(R.id.bottomSheet)
    BottomSheetLayout bottomSheet;
    private EditUserInfoAdapter adapter;
    private CityPickerFragment apf;

    public static EditUserInfoFragment newInstance() {
        return new EditUserInfoFragment();
    }

    @OnClick(R.id.back)
    public void onClick(View view) {
        clickBack();
    }

    @Override
    protected void onInit() {
        super.onInit();
        initList();
    }

    /**
     * 初始化用户信息列表
     */
    private void initList() {
        rvUserInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new EditUserInfoAdapter();
        rvUserInfo.setAdapter(adapter);
    }

    @Override
    protected void onListener() {
        super.onListener();
        rvUserInfo.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (i) {
                    case C.euii.HEAD:
                        editHead();
                        break;
                    case C.euii.NICKNAME:
                        editInfo(R.string.edit_nickname);
                        break;
                    case C.euii.SEX:
                        editSex();
                        break;
                    case C.euii.PHONE:
                        editInfo(R.string.edit_phone_number);
                        break;
                    case C.euii.CITY:
                        editCity();
                        break;
                    case C.euii.BIRTHDAY:
                        editBirthday();
                        break;
                    case C.euii.EMAIL:
                        editInfo(R.string.edit_email);
                        break;
                    case C.euii.PASSWORD:
                        editPassword();
                        break;
                    case C.euii.SIGN:
                        editSign();
                        break;
                }
            }
        });
    }

    /**
     * 修改城市
     */
    private void editCity() {
        if (apf == null) {
            apf = new CityPickerFragment();
        }
        apf.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(), R.id.bottomSheet);
        apf.setListener(new CityPickerFragment.CityListener() {
            @Override
            public void value(String city) {
                presenter.requestChangeCity(city);
            }
        });
    }

    /**
     * 修改签名
     */
    private void editSign() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SIGN_TYPE)
                .setTitleText(getString(R.string.update_sign))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        presenter.requestChangeSign(sweetAlertDialog.getSign());
                    }
                })
                .show();
    }

    /**
     * 更改密码
     */
    private void editPassword() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.PASSWORD_TYPE)
                .setTitleText(getString(R.string.update_password))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        String[] pwd = sweetAlertDialog.getPassword();
                        presenter.requestChangePassword(pwd[0], pwd[1]);
                    }
                }).show();
    }

    /**
     * 编辑生日
     */
    private void editBirthday() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    /**
     * 编辑性别
     */
    private void editSex() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SEX_TYPE)
                .setTitleText("更改性别")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        presenter.requestChangeSex(sweetAlertDialog.getSex());
                    }
                }).show();
    }

    /**
     * 编辑昵称/手机号
     */
    private void editInfo(final int titleId) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.EDIT_TYPE)
                .setTitleText(getString(titleId))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        switch (titleId) {
                            case R.string.edit_nickname:
                                presenter.requestChangeNickname(sweetAlertDialog.getEditText());
                                break;
                            case R.string.edit_phone_number:
                                presenter.requestChangePhone(sweetAlertDialog.getEditText());
                                break;
                            case R.string.edit_email:
                                presenter.requestChangeEmail(sweetAlertDialog.getEditText());
                                break;
                        }
                    }
                })
                .show();
    }


    /**
     * 编辑头像
     */
    private void editHead() {
        BottomSheetImagePicker
                .getInstance()
                .showImagePicker(BottomSheetImagePicker.PickerType.BOTH,
                        getActivity(),
                        bottomSheet,
                        new BottomSheetImagePicker.Listener() {
                            @Override
                            public void onImageArrived(Uri selectedImageUri) {
                                String imgPath = FileUtils.getImageAbsolutePath(getActivity(), selectedImageUri);
                                if (FileUtils.isGif(imgPath)) {
                                    presenter.requestChangeHeadPic(imgPath);
                                    return;
                                }
                                Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
                                Crop.of(selectedImageUri, destination).asSquare().start(getActivity());
                            }
                        });
    }

    @Override
    public void changeHeadPicSuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_head_success));
        adapter.update();
        FragmentFactory.updatedUser();
        setUpdatedUser(false);
    }

    private void update() {
        adapter.update();
        setUpdatedUser(false);
        FragmentFactory.updatedUser();
    }

    @Override
    public void changeHeadPicFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void changeNicknameSuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_nickname_success));
        update();
    }

    @Override
    public void changeNicknameFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void changePhoneSuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_phone_success));
        update();
    }

    @Override
    public void changePhoneFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void changeSignSuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_sign_success));
        update();
    }

    @Override
    public void changeSignFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void changeCitySuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_city_success));
        update();
    }

    @Override
    public void changeCityFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void changeSexSuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_sex_success));
        update();
    }

    @Override
    public void changeSexFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void changeBirthdaySuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_birthday_succes));
        update();
    }

    @Override
    public void changeBirthdayFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void changeEmailSuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_email_success));
        update();
    }

    @Override
    public void changeEmailFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void changePasswordSuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.update_password_succss));
        update();
    }

    @Override
    public void changePasswordFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_user_info;
    }

    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        setDeleted(true);
        MainActivity.startFragment(C.fragment.USER_INFO);
        return true;
    }

    /**
     * BOTTOM SHEET IMAGE PICKER METHODS
     **/
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        BottomSheetImagePicker.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Integer.compare(requestCode, Crop.REQUEST_CROP) == 0) {
            handleCrop(resultCode, data);
            return;
        }
        BottomSheetImagePicker.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
//            resultView.setImageURI(Crop.getOutput(result));
            presenter.requestChangeHeadPic(FileUtils.getImageAbsolutePath(getActivity(), Crop.getOutput(result)));

        } else if (resultCode == Crop.RESULT_ERROR) {
            ToastUtil.getInstance().showLongT(Crop.getError(result).getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (apf != null) {
            ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().remove(apf);
        }
        BottomSheetImagePicker.getInstance().destroy();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        try {
            presenter.requestBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
