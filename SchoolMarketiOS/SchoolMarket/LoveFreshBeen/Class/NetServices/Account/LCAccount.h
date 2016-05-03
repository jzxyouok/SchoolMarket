//
//  LCAccount.h
//  TripVip
//
//  Created by HuZeSen on 15/7/23.
//  Copyright (c) 2015年 GZLC. All rights reserved.
//

#import <Foundation/Foundation.h>
//#import "PostCacheModel.h"
//#import "UploadFileRequest.h"
#import "UserProfileItems.h"

typedef NS_ENUM(NSInteger, CacheStatusType)   // 缓存标记类型
{
    CacheStatusTypeNormal = 0,     // 正常情况,比如发送成功
    CacheStatusTypeFailed = 1,       // 发送失败,可能是服务器报错,也可能是断网,需要重发的
    CacheStatusTypeAbandon = 2   //  重发也会失败的
};

@class UserProfileItems;

/**
 *  建议不要直接使用这个类,而是建立一个继承的子类,然后添加具体的属性等.
 */
@interface LCAccount : NSObject

// ------------ 常用属性 -------------------
// 如果退出登录需要清掉的数据,属性的key要带上 lc_ 这个前缀
@property (nonatomic) BOOL logined;       // 是否登录
@property (nonatomic) BOOL imLogined;  // 是否登录IM
@property (nonatomic, copy) NSString *userId;
@property (nonatomic, copy) NSString *m_ids;         //我的药箱的所有药品id eg："1,2,3,4"
@property (nonatomic, copy) NSString *sessionId;         // 登录
@property (nonatomic, copy) NSString *mobileNum;      // 手机号码
@property (nonatomic, copy) NSArray *recieveNotify;   // 消息通知打开或者关闭的状态

// ------------ 个人信息model --------------------
// 因为每个项目,model的属性都不一样,因此建议修改UserProfileItems这个类的属性
@property (nonatomic, strong) UserProfileItems *userInfo;

// ------------ 发布的帖子 ----------------
@property (nonatomic, strong) NSMutableArray *postsSentArray;  // 已经发送成功的帖子

+ (instancetype)sharedInstance;

/**
 *  获取个人信息文件(以手机号为唯一标识)
 *
 */
//- (PostCacheModel *)getPostsCacheModel;

/**
 *  重新设置正常队列model的时间戳
 *
 *  @param cacheModel 目标model
 *  @param index      model在数组中的索引值
 */
//- (void)resetTimeintervalWithModel:(CacheModel *)cacheModel Index:(NSInteger)index;

/**
 *  重新设置重发队列model的时间戳
 *
 *  @param cacheModel 目标model
 *  @param index      model在数组中的索引值
 */
//- (void)resetTimeintervalWithResendModel:(CacheModel *)cacheModel Index:(NSInteger)index;

/**
 *  保存发布的缓存
 *
 *  @param urlArray 图片链接
 *  @param o_id     帖子idid
 *  @param fileType 上传图片类型
 */
//- (void)cacheCatchWithFileUrlArray:(NSArray <NSURL *> *)urlArray ownerId:(NSInteger)o_id uploadFileType:(UploadFileType)fileType;

/**
 *  保存发布的缓存,但可以指定第一张图片的索引
 *
 *  @param urlArray  图片链接路径
 *  @param o_id      帖子id
 *  @param fileType  文件类型
 *  @param baseIndex 第一张图片指定的索引
 */
//- (void)cacheCatchWithFileUrlArray:(NSArray <NSURL *> *)urlArray ownerId:(NSInteger)o_id uploadFileType:(UploadFileType)fileType baseImgIndex:(NSInteger)baseIndex;

/**
 *  缓存视频
 *
 *  @param o_id     帖子id
 *  @param urlArray 文件路径,注意第一个为图片路径,第二个为视频路径
 */
//- (void)cacheVideoFileWithOwner_id:(NSInteger)o_id fileURLArray:(NSArray<NSURL *> *)urlArray uploadFileTypeArray:(NSArray<NSNumber *> *)typeArray;

/**
 *  标记发送的帖子图片
 */
- (void)markFirstModel:(CacheStatusType)status;

/**
 *  标记重发队列的帖子图片
 */
- (void)markResendArrayFirstModel:(CacheStatusType)status;

// 退出登录
- (void)logOut;
/**
 *  获取本地缓存用户个人信息的model
 */
+ (UserProfileItems *)getUserProfileItems;

/**
 *  更新本地缓存用户个人信息的model
 */
+ (void)updateUserProfileItems:(UserProfileItems *)newItem;

@end
