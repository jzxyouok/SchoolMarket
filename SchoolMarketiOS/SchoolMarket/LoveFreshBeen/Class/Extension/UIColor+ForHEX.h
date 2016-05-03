//
//  UIColor+ForHEX.h
//  LCFoundationLib
//
//  Created by HuZeSen on 15/7/27.
//  Copyright (c) 2015年 GZLC. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIColor (ForHEX)

/**
 *  将颜色转换成 HEX 字符串
 */
+ (NSString *)hexFromUIColor: (UIColor*) color;

/**
 *  将 16进制 颜色换成 UIColor
 */
+ (UIColor*)colorWithHex:(NSInteger)hexValue;

+ (UIColor*)colorWithHex:(NSInteger)hexValue alpha:(CGFloat)alphaValue;

@end
