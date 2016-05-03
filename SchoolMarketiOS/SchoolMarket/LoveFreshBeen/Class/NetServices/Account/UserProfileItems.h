//
//  UserProfileItems.h
//  fish
//
//  Created by HuZeSen on 15/12/9.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#import <Foundation/Foundation.h>

@class UserProfileItem,TagItem;
@interface UserProfileItems : NSObject


@property (nonatomic, assign) NSInteger ret_code;

@property (nonatomic, copy) NSString *err;

@property (nonatomic, strong) UserProfileItem *data;

@end

@interface UserProfileItem : NSObject

@property (nonatomic, copy) NSString *u_description;

@property (nonatomic, copy) NSString *integral;

@property (nonatomic, copy) NSString *catch_count;

@property (nonatomic, copy) NSString *follow_count;

@property (nonatomic, copy) NSString *fans_count;

@property (nonatomic, copy) NSString *nick;

@property (nonatomic, copy) NSString *fish_number;

@property (nonatomic, copy) NSString *username;

@property (nonatomic, copy) NSString *role;

@property (nonatomic, copy) NSString *face_b;

@property (nonatomic, copy) NSString *u_id;

@property (nonatomic, copy) NSString *face_m;

@property (nonatomic, copy) NSString *gender;

@property (nonatomic, copy) NSString *face_s;

@property (nonatomic, copy) NSString *shipping_address;

@property (nonatomic, copy) NSString *province_id;

@property (nonatomic, copy) NSString *city_id;

@property (nonatomic, copy) NSString *birthday;

@property (nonatomic, copy) NSString *phone;

@property (nonatomic, copy) NSString *allow_push_chat;

@property (nonatomic, copy) NSString *allow_push_notice;

@property (nonatomic, copy) NSString *shop_count;

@property (nonatomic, copy) NSString *city_name;

@property (nonatomic, copy) NSString *province_name;

@property (nonatomic, copy) NSString *ground_count;

@property (nonatomic, copy) NSString *age;

@property (nonatomic, copy) NSString *job;

@property (nonatomic, copy) NSString *invite;

@property (nonatomic, copy) NSString *have_team;
/**
 *  微信uid
 */
@property (nonatomic, copy) NSString *o_uid;
/**
 *  QQuid
 */
@property (nonatomic, copy) NSString *qq_uid;

/**
 *   微博绑定id，为空表示未绑定
 */
@property (nonatomic, copy) NSString *weibo_uid;

@property (nonatomic, copy) NSString *grade;

@property (nonatomic, copy) NSString *body_height;

@property (nonatomic, copy) NSString *body_weight;

@property (nonatomic, copy) NSString *field_position;

@property (nonatomic, copy) NSString *kick;

@property (nonatomic, copy) NSString *boots;

@property (nonatomic, copy) NSString *moments;

@property (nonatomic, copy) NSString *number;

@property (nonatomic, copy) NSString *team_id;

@property (nonatomic, copy) NSString *matches;

@property (nonatomic, copy) NSString *goals;

@property (nonatomic, copy) NSString *assists;

@property (nonatomic, copy) NSString *field_position_id;

@property (nonatomic, copy) NSString *win_count;

@property (nonatomic, copy) NSString *win_rate;

@property (nonatomic, copy) NSString *draw_count;

@property (nonatomic, copy) NSString *draw_rate;

@property (nonatomic, copy) NSString *lose_count;

@property (nonatomic, copy) NSString *lose_rate;

@property (nonatomic, copy) NSString *goal_count;

@property (nonatomic, copy) NSString *assists_count;

@property (nonatomic, copy) NSString *match_count;

@property (nonatomic, copy) NSString *avg_goal_count;

@property (nonatomic, copy) NSString *avg_assists_count;

@property (nonatomic, copy) NSString *moment_count;

@property (nonatomic, strong) NSArray <TagItem *> *tags;

@property (nonatomic, copy) NSString *zone_name;

@property (nonatomic, copy) NSString *zone_id;

@property (nonatomic, copy) NSString *home_town_province_id;

@property (nonatomic, copy) NSString *home_town_province_name;

@property (nonatomic, copy) NSString *home_town_city_id;

@property (nonatomic, copy) NSString *home_town_city_name;

@property (nonatomic, copy) NSString *home_town_zone_id;

@property (nonatomic, copy) NSString *home_town_zone_name;

@property (nonatomic, copy) NSString *cover;
@end

@interface TagItem : NSObject

@property (nonatomic, copy) NSString *tag_id;

@property (nonatomic, copy) NSString *tag;


@end
/**
 *  
 weibo_uid 微博绑定id，为空表示未绑定
 body_height: 身高
 "body_weight": 体重
 "field_position": 场上位置
 "kick": 擅长脚
 "boots": 战靴
 "moments": 时刻数
 "number": 球衣号码
 "team_id": 所属球队id
 "matches":出场数
 "goals":进球数
 "assists":助攻数
 */
