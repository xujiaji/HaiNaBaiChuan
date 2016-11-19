package io.xujiaji.sample.model;

/**
 * Created by jiana on 16-11-19.
 */

public class DataFill {
    public static String getText() {
        return "# XMVP\n" +
                "Extract the MVP from the project\n" +
                "\n" +
                "# XMVP UML\n" +
                "![XMPV_UML](mvp.svg)\n" +
                "\n" +
                "# How to use?\n" +
                "### step1:define a contract\n" +
                "You need to define a contract in contracts package, it contains a extend 'Contract.BasePresenter' interface and a extend 'Contract.BaseView' interface.\n" +
                "> example:MainContract\n" +
                "\n" +
                "``` java\n" +
                "public interface MainContract {\n" +
                "    interface Presenter extends Contract.BasePresenter{\n" +
                "        void requestTextData();\n" +
                "    }\n" +
                "    interface View extends Contract.BaseView{\n" +
                "        void showText(String text);\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "### step2:need modle deal data\n" +
                "> example:I create 'DataFill.java' file in model packege\n" +
                "\n" +
                "``` java\n" +
                "public class DataFill {\n" +
                "    public static String getText() {\n" +
                "        return \"....\" ;\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "### step3:add a Presenter\n" +
                "> example: MainPresenter.java\n" +
                "\n" +
                "``` java\n" +
                "public class MainPresenter extends BasePresenter <MainContract.View> implements MainContract.Presenter {\n" +
                "    public MainPresenter(MainContract.View view) {\n" +
                "        super(view);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public void requestTextData() {\n" +
                "        String textData = DataFill.getText();\n" +
                "        view.showText(textData);\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "### step4:add a View\n" +
                "> example: MainActivity.java\n" +
                "\n" +
                "``` java\n" +
                "public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {\n" +
                "    @BindView(R.id.tvText)\n" +
                "    TextView tvText;\n" +
                "\n" +
                "    @Override\n" +
                "    protected int getContentId() {\n" +
                "        return R.layout.activity_main2;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    @Override\n" +
                "    public void showText(String text) {\n" +
                "        tvText.setText(text);\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "\n" +
                "# License\n" +
                "```\n" +
                "   Copyright 2016 XuJiaji\n" +
                "\n" +
                "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "   you may not use this file except in compliance with the License.\n" +
                "   You may obtain a copy of the License at\n" +
                "\n" +
                "       http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "   Unless required by applicable law or agreed to in writing, software\n" +
                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "   See the License for the specific language governing permissions and\n" +
                "   limitations under the License.\n" +
                "```\n";
    }
}
