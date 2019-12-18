package com.appler.universitysystem.model.im;

import com.appler.universitysystem.base.IModel;

public interface IDocsModel extends IModel {

    void getDocsList(String string);

    void getFlowFile(String string);

}
