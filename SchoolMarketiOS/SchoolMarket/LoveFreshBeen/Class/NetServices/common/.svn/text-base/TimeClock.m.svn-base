//
//  TimeClock.m
//  fish
//
//  Created by HuZeSen on 15/12/2.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#import "TimeClock.h"

@interface TimeClock ()
{
    NSTimeInterval history_time;
}
@property (nonatomic) NSTimeInterval timeInterval;

+ (instancetype)sharedInstance;

@end

@implementation TimeClock

+ (instancetype)sharedInstance
{
    static id clock = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        clock = [[TimeClock alloc]init];
    });
    return clock;
}

- (void)setTimeInterval:(NSTimeInterval)timeInterval
{
    _timeInterval = timeInterval;
}

- (NSTimeInterval)getTimeInterval
{
    NSTimeInterval current = [[NSDate date] timeIntervalSince1970];
    if (self.timeInterval == 0)
    {
        self.timeInterval = current;
        history_time = current;
    }
    else
    {
        self.timeInterval += current - (history_time);
        history_time = current;
    }
    return self.timeInterval;
}

@end
