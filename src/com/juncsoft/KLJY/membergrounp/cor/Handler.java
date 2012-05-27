package com.juncsoft.KLJY.membergrounp.cor;

import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;

public interface Handler {
    public void handlerRequest(CorRequest request, MemberApplicationRecord memApp);
}
