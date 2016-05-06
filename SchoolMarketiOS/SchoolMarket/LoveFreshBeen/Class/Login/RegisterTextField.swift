//
//  RegisterTextField.swift
//  LoveFreshBeen
//
//  Created by yich on 16/5/6.
//  Copyright © 2016年 tianzhongtao. All rights reserved.
//

import UIKit

class RegisterTextField: UIView {
    
    private var bacView:UIView?
    private var textField:UITextField?

    override init(frame: CGRect) {
        super.init(frame: frame)
        
        self.backgroundColor = UIColor.clearColor()
        
        let width = frame.size.width
        let height = frame.size.height
        bacView = UIView(frame: CGRectMake(30, 0, width-60, height))
        bacView!.layer.cornerRadius = 5.0
        bacView!.backgroundColor = UIColor.whiteColor()
        self.addSubview(bacView!)
        
        textField = UITextField(frame: CGRectMake(15, 0, width-60, height))
        textField?.font = UIFont.systemFontOfSize(18)
        bacView!.addSubview(textField!)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    convenience init(frame: CGRect, placeholder: String, tag: Int) {
        self.init(frame: frame)
        
        textField?.placeholder = placeholder
        textField?.tag = tag
    }

}
