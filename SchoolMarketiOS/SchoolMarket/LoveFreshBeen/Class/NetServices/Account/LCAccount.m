//
//  LCAccount.m
//  TripVip
//
//  Created by HuZeSen on 15/7/23.
//  Copyright (c) 2015年 GZLC. All rights reserved.
//
 
#import "LCAccount.h"
#import "UserProfileItems.h"

// 如果退出登录需要清掉的数据,带上 lc_ 这个前缀
static NSString *const loginedKey = @"lc_logined";                      // 是否登录
static NSString *const deviceTokenKey = @"deviceToken";           // 设备令牌
static NSString *const sessionIdKey = @"lc_sessionId";               //  身份识别
static NSString *const userIdKey = @"lc_userIdKey";
static NSString *const m_idsIdKey = @"lc_m_idsIdKey";
static NSString *const recieveNotifyKey = @"lc_recieveNotifyKey";
static NSString *const mobileKey = @"lc_mobileKey";

#define GET_USER_DEFAULTS(v) NSUserDefaults *v = [NSUserDefaults standardUserDefaults];
#define ProfileName @"Profile"
#define TeamInfoName @"TeamInfo"
#define PostCachePath @"PostCache"

@interface LCAccount ()<NSXMLParserDelegate>

@property (nonatomic, weak) NSArray *districtArr;
@property (nonatomic, strong) dispatch_queue_t queue;

@end

@implementation LCAccount

+ (instancetype)sharedInstance
{
    static id account = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        account = [[LCAccount alloc]init];
    });
    return account;
}

- (dispatch_queue_t)queue
{
    if (!_queue)
    {
        _queue = dispatch_queue_create("", DISPATCH_QUEUE_CONCURRENT);
    }
    return _queue;
}

- (NSMutableArray *)postsSentArray
{
    if (!_postsSentArray)
    {
        _postsSentArray = [[NSMutableArray alloc]initWithCapacity:0];
    }
    return _postsSentArray;
}
#pragma mark- setting method

- (void)setLogined:(BOOL)logined
{
    [self setObject:@(logined) forKey:loginedKey];
}

- (void)setDeviceToken:(NSString *)deviceToken
{
    [self setObject:deviceToken forKey:deviceTokenKey];
}

- (void)setSessionId:(NSString *)sessionId
{
    [self setObject:sessionId forKey:sessionIdKey];
}

- (void)setUserId:(NSString *)userId
{
    [self setObject:userId forKey:userIdKey];
}

- (void)setM_ids:(NSString *)m_ids {
    [self setObject:m_ids forKey:m_idsIdKey];
}

- (void)setRecieveNotify:(NSArray *)recieveNotify
{
    [self setObject:recieveNotify forKey:recieveNotifyKey];
}

//- (void)setUserInfo:(UserProfileItems *)userInfo
//{
//    [self cacheUserProfile:userInfo];
//}

//- (void)setMobileNum:(NSString *)mobileNum
//{
//    [self setObject:mobileNum forKey:mobileKey];
//    UserProfileItems *item = [[UserProfileItems alloc]init];
//    [self cacheUserProfile:item];
//}
#pragma mark- getting method
- (BOOL)logined
{
    NSNumber *num = [self getObjectWithKey:loginedKey];
    return num.boolValue;
}

- (NSString *)deviceToken
{
    return [self getObjectWithKey:deviceTokenKey];
}

- (NSString *)sessionId
{
    return [self getObjectWithKey:sessionIdKey];
}

- (NSString *)userId
{
    return [self getObjectWithKey:userIdKey];
}

- (NSString *)m_ids {
    return [self getObjectWithKey:m_idsIdKey];
}

- (NSString *)mobileNum
{
    return [self getObjectWithKey:mobileKey];
}

//- (UserProfileItems *)userInfo
//{
//    NSString *path = [self getFilePath];
//    UserProfileItems *items_ = [NSKeyedUnarchiver unarchiveObjectWithFile:[path stringByAppendingPathComponent:ProfileName]];
//    return items_;
//}

+ (UserProfileItems *)getUserProfileItems {
    LCAccount *act = [LCAccount sharedInstance];
    UserProfileItems *item = act.userInfo;
    return item;
}

+ (void)updateUserProfileItems:(UserProfileItems *)newItem {
    LCAccount *act = [LCAccount sharedInstance];
    UserProfileItems *item = act.userInfo;
    if ([item isEqual:newItem]) {
        return;
    }else {
        item = newItem;
    }
    act.userInfo = item;
}

//#pragma mark- 帖子缓存相关
//
//- (NSString *)getPostsCacheFilePath
//{
//    NSString *path = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject];
//    return [path stringByAppendingPathComponent:PostCachePath];
//}
//
//#pragma mark 图片缓存
//- (void)cacheCatchWithFileUrlArray:(NSArray <NSURL *> *)urlArray ownerId:(NSInteger)o_id uploadFileType:(UploadFileType)fileType
//{
//    [self cacheCatchWithFileUrlArray:urlArray ownerId:o_id uploadFileType:fileType baseImgIndex:1];
//}
//
//- (void)cacheCatchWithFileUrlArray:(NSArray<NSURL *> *)urlArray ownerId:(NSInteger)o_id uploadFileType:(UploadFileType)fileType baseImgIndex:(NSInteger)baseIndex
//{
//    NSMutableArray *temp = [[NSMutableArray alloc]initWithCapacity:urlArray.count];
//    for (int index = 0; index < urlArray.count; index ++) {
//        [temp addObject:@(fileType)];
//    }
//    [self cacheFileWithOwnerId:o_id fileUrlArray:urlArray uploadFileTypeArray:temp baseImgIndex:baseIndex];
//}
//
//#pragma mark 视频缓存
//- (void)cacheFileWithOwnerId:(NSInteger)o_id fileUrlArray:(NSArray<NSURL *> *)urlArray uploadFileTypeArray:(NSArray<NSNumber *> *)typeArray
//{
//    [self cacheFileWithOwnerId:o_id fileUrlArray:urlArray uploadFileTypeArray:typeArray baseImgIndex:1];
//}
//
//- (void)cacheFileWithOwnerId:(NSInteger)o_id fileUrlArray:(NSArray<NSURL *> *)urlArray uploadFileTypeArray:(NSArray<NSNumber *> *)typeArray baseImgIndex:(NSInteger)baseImgIndex
//{
//    NSString *path_cache = [self getPostsCacheFilePath];
//    __block PostCacheModel *c_model = nil;
//    typeof(self) __weak weakSelf = self;
//    dispatch_barrier_sync(self.queue, ^{
//        c_model = [NSKeyedUnarchiver unarchiveObjectWithFile:path_cache];
//        NSMutableArray<CacheModel *> *insideArray = [[NSMutableArray alloc]initWithCapacity:urlArray.count];
//        [urlArray enumerateObjectsUsingBlock:^(NSURL * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
//            CacheModel *cacheModel = [[CacheModel alloc]init];
//            cacheModel.type = [typeArray[idx] integerValue];
//            cacheModel.fileUrl = obj;
//            cacheModel.index = idx + baseImgIndex;
//            cacheModel.sessionId = weakSelf.sessionId;
//            cacheModel.catch_id = o_id;
//            [insideArray addObject:cacheModel];
//        }];
//        [self cacheNewCacheArray:insideArray cacheModel:c_model];
//    });
//}
//
//#pragma mark 保存缓存
//- (void)cacheNewCacheArray:(NSArray<CacheModel *> *)cacheArray cacheModel:(PostCacheModel *)c_model
//{
//    NSString *path_cache = [self getPostsCacheFilePath];
//    
//    BOOL hasCached = false;
//    for (NSString *keyString in c_model.cacheDic.allKeys)
//    {
//        if ([keyString isEqualToString:self.mobileNum])
//        {
//            hasCached = YES;
//            break;
//        }
//    }
//    
//    PostsArrayModel *postsModel = nil;
//    if (hasCached)
//    {
//        postsModel = c_model.cacheDic[self.mobileNum];
//    }
//    else
//    {
//        postsModel = [[PostsArrayModel alloc]init];
//    }
//    NSMutableArray *mutArr = nil;
//    if (postsModel.postsArray)
//    {
//        mutArr = [postsModel.postsArray mutableCopy];
//    }else
//    {
//        mutArr = [[NSMutableArray alloc]initWithCapacity:0];
//    }
//    [mutArr addObject:cacheArray];
//    postsModel.postsArray = mutArr;
//    
//    NSMutableDictionary *mutDic = [c_model.cacheDic mutableCopy];
//    [mutDic setValue:postsModel forKey:self.mobileNum];
//    c_model.cacheDic = mutDic;
//    
//    BOOL result =  [NSKeyedArchiver archiveRootObject:c_model toFile:path_cache];
//    if (result)
//    {
//        NSLog(@"保存缓存成功");
//    }
//}
//#pragma mark 标记被发送的缓存
//- (void)markFirstModel:(CacheStatusType)status
//{
//    NSString *path_cache = [self getPostsCacheFilePath];
//    __block PostCacheModel *c_model = nil;
//    WEAKSELF
//    dispatch_barrier_sync(self.queue, ^{
//        c_model = [NSKeyedUnarchiver unarchiveObjectWithFile:path_cache];
//        
//        PostsArrayModel *model = c_model.cacheDic[weakSelf.mobileNum];
//        NSMutableArray *mut = model.postsArray.mutableCopy;
//        if (status == 0) // 帖子图片等 已经全部发送完毕
//        {
//            NSMutableArray<CacheModel *> *insideArray = [mut.firstObject mutableCopy];
//            __block NSInteger lastSentModelIndex = 0; // 刚发送完毕的model
//            [insideArray enumerateObjectsUsingBlock:^(CacheModel * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
//
//                if (obj.beSent == NO)
//                {
//                    lastSentModelIndex = idx;
//                    obj.beSent = YES;
//                    *stop = YES;
//                }
//            }];
//            if (lastSentModelIndex == (insideArray.count - 1))
//            {
//                if (insideArray.firstObject.type)
//                {
//                    //视频与缩略图URL：self.videoFileURL self.videoCoverFileURL
//                    //发布成功后删除视频文件
//                    //    [LCShortVideToolKit deleteVideoFile:self.videoFileURL andVideoCoverFile:self.videoCoverFileURL];
////                    [LCShortVideToolKit deleteVideoFile:insideArray.lastObject.fileUrl andVideoCoverFile:insideArray.firstObject.fileUrl];
//                }
//                [self.postsSentArray addObject:mut.firstObject];
//                [mut removeObjectAtIndex:0];
//            }
//        }
//        else if (status == 1)   // 发送失败,但可以重发的
//        {
//            NSMutableArray *resendMut = [[NSMutableArray alloc]initWithCapacity:0];
//            [resendMut addObjectsFromArray:model.resendArray];
//            [resendMut addObjectsFromArray:mut];
//            model.resendArray = resendMut;
//            [mut removeAllObjects];
//        }
//        else   // 发送失败,不可重发
//        {
//            [mut removeObjectAtIndex:0];
//        }
//        
//        model.postsArray = mut;
//        NSMutableDictionary *mutDic = [c_model.cacheDic mutableCopy];
//        [mutDic setValue:model forKey:weakSelf.mobileNum];
//        c_model.cacheDic = mutDic;
//        BOOL result = [NSKeyedArchiver archiveRootObject:c_model toFile:path_cache];
//        if (result)
//        {
//            NSLog(@"成功标记已发送的缓存");
//        }
//    });
//}
//
//- (void)markResendArrayFirstModel:(CacheStatusType)status
//{
//    NSString *path_cache = [self getPostsCacheFilePath];
//    __block PostCacheModel *c_model = nil;
//    WEAKSELF
//    dispatch_barrier_sync(self.queue, ^{
//        c_model = [NSKeyedUnarchiver unarchiveObjectWithFile:path_cache];
//        
//        PostsArrayModel *model = c_model.cacheDic[weakSelf.mobileNum];
//        NSMutableArray *mut = model.resendArray.mutableCopy;
//        if (status == 0)
//        {
//            NSMutableArray<CacheModel *> *insideArray = [mut.firstObject mutableCopy];
//            __block NSInteger lastSentModelIndex = 0; // 刚发送完毕的model
//            [insideArray enumerateObjectsUsingBlock:^(CacheModel * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
//                
//                if (obj.beSent == NO)
//                {
//                    lastSentModelIndex = idx;
//                    obj.beSent = YES;
//                    *stop = YES;
//                }
//            }];
//            if (lastSentModelIndex == (insideArray.count - 1))
//            {
//                if (insideArray.firstObject.type)
//                {
//                    //视频与缩略图URL：self.videoFileURL self.videoCoverFileURL
//                    //发布成功后删除视频文件
//                    //    [LCShortVideToolKit deleteVideoFile:self.videoFileURL andVideoCoverFile:self.videoCoverFileURL];
////                    [LCShortVideToolKit deleteVideoFile:insideArray.lastObject.fileUrl andVideoCoverFile:insideArray.firstObject.fileUrl];
//                }
//                [mut removeObjectAtIndex:0];
//            }
//        }
//        else if (status == 2)
//        {
//            [mut removeObjectAtIndex:0];
//        }
//        
//        model.resendArray = mut;
//        NSMutableDictionary *mutDic = [c_model.cacheDic mutableCopy];
//        [mutDic setValue:model forKey:weakSelf.mobileNum];
//        c_model.cacheDic = mutDic;
//        BOOL result = [NSKeyedArchiver archiveRootObject:c_model toFile:path_cache];
//        if (result)
//        {
//            NSLog(@"成功标记已重新发送的缓存");
//        }
//    });
//}
//#pragma mark- 重设帖子缓存的时间戳
//- (void)resetTimeintervalWithModel:(CacheModel *)cacheModel Index:(NSInteger)index
//{
//    __block PostCacheModel *c_model = nil;
//    NSString *path_cache = [self getPostsCacheFilePath];
//    WEAKSELF
//    dispatch_barrier_sync(self.queue, ^{
//        // 更改数据
//        NSMutableDictionary *modelDic = [[weakSelf getPostsCacheModel].cacheDic mutableCopy];
//        PostsArrayModel *model = modelDic[weakSelf.mobileNum];
//        NSMutableArray<NSArray<CacheModel *> *> *postsArray = [model.postsArray mutableCopy];
//        NSMutableArray<CacheModel *> *firstPostsArray = [postsArray.firstObject mutableCopy];
//        
//        [firstPostsArray replaceObjectAtIndex:index withObject:cacheModel];
//        [postsArray replaceObjectAtIndex:0 withObject:firstPostsArray];
//        model.postsArray = postsArray;
//        [modelDic setValue:model forKey:weakSelf.mobileNum];
//        c_model = [NSKeyedUnarchiver unarchiveObjectWithFile:path_cache];
//        c_model.cacheDic = modelDic;
//        // 保存到本地
//        BOOL result = [NSKeyedArchiver archiveRootObject:c_model toFile:path_cache];
//        if (result)
//        {
//            NSLog(@"成功重新设置普通队列的时间戳");
//        }
//    });
//}
//
//- (void)resetTimeintervalWithResendModel:(CacheModel *)cacheModel Index:(NSInteger)index
//{
//    __block PostCacheModel *c_model = nil;
//    NSString *path_cache = [self getPostsCacheFilePath];
//    WEAKSELF
//    dispatch_barrier_sync(self.queue, ^{
//        // 更改数据
//        NSMutableDictionary *modelDic = [[weakSelf getPostsCacheModel].cacheDic mutableCopy];
//        PostsArrayModel *model = modelDic[weakSelf.mobileNum];
//        NSMutableArray<NSArray<CacheModel *> *> *resendArray = [model.resendArray mutableCopy];
//        NSMutableArray<CacheModel *> *firstPostsArray = [resendArray.firstObject mutableCopy];
//        
//        [firstPostsArray replaceObjectAtIndex:index withObject:cacheModel];
//        [resendArray replaceObjectAtIndex:0 withObject:firstPostsArray];
//        model.resendArray = resendArray;
//        [modelDic setValue:model forKey:weakSelf.mobileNum];
//        c_model = [NSKeyedUnarchiver unarchiveObjectWithFile:path_cache];
//        c_model.cacheDic = modelDic;
//        // 保存到本地
//        BOOL result = [NSKeyedArchiver archiveRootObject:c_model toFile:path_cache];
//        if (result)
//        {
//            NSLog(@"成功重新设置失败队列的时间戳");
//        }
//    });
//}

#pragma mark- method

//- (PostCacheModel *)getPostsCacheModel
//{
//    NSString *path = [self getPostsCacheFilePath];
//    PostCacheModel *model = [NSKeyedUnarchiver unarchiveObjectWithFile:path];
//    return model;
//}
//
//- (void)cacheUserProfile:(UserProfileItems *)items
//{
//    NSFileManager *manager = [NSFileManager defaultManager];
//    NSString *path = [self getFilePath];
//    NSString *path_profile = [path stringByAppendingPathComponent:ProfileName];
//    if (![manager fileExistsAtPath:path])
//    {
//        [manager createDirectoryAtPath:path withIntermediateDirectories:YES attributes:nil error:nil];
//        PostCacheModel *model = [[PostCacheModel alloc]init];
//        BOOL result = [NSKeyedArchiver archiveRootObject:model toFile:[self getPostsCacheFilePath]];
//        if (result)
//        {
//            NSLog(@"创建帖子缓存成功");
//        }
//    }
//    BOOL result_profile = [NSKeyedArchiver archiveRootObject:items toFile:path_profile];
//    if (result_profile)
//    {
//        NSLog(@"保存个人资料成功");
//    }
//}
//
//- (NSString *)getFilePath
//{
//    NSString *documents = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject];
//    NSString *path = [documents stringByAppendingPathComponent:self.mobileNum];
//    return path;
//}

- (void)setObject:(id)object forKey:(NSString *)key
{
    GET_USER_DEFAULTS(defaults);
    [defaults setObject:object forKey:key];
    [defaults synchronize];
}

- (id)getObjectWithKey:(NSString *)key
{
    GET_USER_DEFAULTS(defaults);
    return [defaults objectForKey:key];
}

- (void)logOut
{
    GET_USER_DEFAULTS(defaults);
    NSDictionary *dic = [defaults dictionaryRepresentation];
    for (NSString *object in dic.allKeys)
    {
        if (![object hasPrefix:@"lc_"])
        {
            continue;
        }
        [defaults removeObjectForKey:object];
    }
    [defaults synchronize];
}

@end
