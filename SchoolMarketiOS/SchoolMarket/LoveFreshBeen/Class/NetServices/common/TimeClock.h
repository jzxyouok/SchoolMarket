//
//  TimeClock.h
//  fish
//
//  Created by HuZeSen on 15/12/2.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TimeClock : NSObject

//@property (nonatomic) NSTimeInterval timeInterval;

+ (instancetype)sharedInstance;

- (NSTimeInterval)getTimeInterval;
- (void)setTimeInterval:(NSTimeInterval)timeInterval;

@end
