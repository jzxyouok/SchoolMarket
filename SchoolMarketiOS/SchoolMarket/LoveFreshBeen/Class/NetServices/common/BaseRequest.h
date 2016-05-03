//
//  BaseRequest.h
//  fish
//
//  Created by HuZeSen on 15/12/1.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#import "YTKRequest.h"

typedef void(^RequestSuccessfulBlock)(YTKBaseRequest *request);
typedef void(^RequestFailedBlock)(YTKBaseRequest *request);

@interface BaseRequest : YTKRequest

@property (nonatomic) BOOL cache;

@property (nonatomic, copy) NSArray<NSString *> *identifiesArr;
@property (nonatomic, copy) NSArray<NSString *> *modelNameArr;

- (instancetype)initWithArgument:(id)argument;

- (void)startWithSuccess:(RequestSuccessfulBlock)sBlock
                 failure:(RequestFailedBlock)fBlock;

- (NSString *)identify;
@end
