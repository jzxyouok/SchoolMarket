//
//  LoginService.swift
//  LoveFreshBeen
//
//  Created by Dora on 16/5/5.
//  Copyright © 2016年 tianzhongtao. All rights reserved.
//

import UIKit

class LoginService: AnyObject {

    class func login(username:String,password:String,sBlock:SuccessfulBlock,fBlock:FailedBlock) {
        CentralProcessingHandler.sendRequestWithIdentify("regist.jsp", param: ["name":username,"password":password], modelName: "LoginItem", successBlock: sBlock, faliedBlock: fBlock);
    }
}
