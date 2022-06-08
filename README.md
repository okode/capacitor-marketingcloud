# capacitor-marketingcloud

MarketingCloud Capacitor Plugin

[![CircleCI](https://circleci.com/gh/okode/capacitor-marketingcloud/tree/main.svg?style=svg)](https://circleci.com/gh/okode/capacitor-marketingcloud/tree/main)

## Install

```bash
npm install @okode/capacitor-marketingcloud
npx cap sync
```

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`setPushToken(...)`](#setpushtoken)
* [`isPushEnabled()`](#ispushenabled)
* [`setPushEnabled(...)`](#setpushenabled)
* [`setProfileId(...)`](#setprofileid)
* [`isMarketingCloudNotification(...)`](#ismarketingcloudnotification)
* [`notifyNotificationOpened(...)`](#notifynotificationopened)
* [`showNotification(...)`](#shownotification)
* [`addListener('notificationOpened', ...)`](#addlistenernotificationopened)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options: { appId: string; accessToken: string; serverUrl: string; enableAnalytics?: boolean; }) => Promise<void>
```

| Param         | Type                                                                                               |
| ------------- | -------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ appId: string; accessToken: string; serverUrl: string; enableAnalytics?: boolean; }</code> |

--------------------


### setPushToken(...)

```typescript
setPushToken(opts: { token: string; }) => void
```

| Param      | Type                            |
| ---------- | ------------------------------- |
| **`opts`** | <code>{ token: string; }</code> |

--------------------


### isPushEnabled()

```typescript
isPushEnabled() => Promise<{ value: boolean; }>
```

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### setPushEnabled(...)

```typescript
setPushEnabled(opts: { enabled: boolean; }) => void
```

| Param      | Type                               |
| ---------- | ---------------------------------- |
| **`opts`** | <code>{ enabled: boolean; }</code> |

--------------------


### setProfileId(...)

```typescript
setProfileId(opts: { value: string; }) => void
```

| Param      | Type                            |
| ---------- | ------------------------------- |
| **`opts`** | <code>{ value: string; }</code> |

--------------------


### isMarketingCloudNotification(...)

```typescript
isMarketingCloudNotification(opts: { notification: any; }) => Promise<{ value: boolean; }>
```

| Param      | Type                                |
| ---------- | ----------------------------------- |
| **`opts`** | <code>{ notification: any; }</code> |

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### notifyNotificationOpened(...)

```typescript
notifyNotificationOpened(opts: { notification: any; }) => void
```

| Param      | Type                                |
| ---------- | ----------------------------------- |
| **`opts`** | <code>{ notification: any; }</code> |

--------------------


### showNotification(...)

```typescript
showNotification(opts: { notification: any; }) => Promise<{ value: boolean; }>
```

| Param      | Type                                |
| ---------- | ----------------------------------- |
| **`opts`** | <code>{ notification: any; }</code> |

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### addListener('notificationOpened', ...)

```typescript
addListener(eventName: 'notificationOpened', listenerFunc: (notification: MarketingCloudNotification) => void) => PluginListenerHandle
```

| Param              | Type                                                                                                         |
| ------------------ | ------------------------------------------------------------------------------------------------------------ |
| **`eventName`**    | <code>'notificationOpened'</code>                                                                            |
| **`listenerFunc`** | <code>(notification: <a href="#marketingcloudnotification">MarketingCloudNotification</a>) =&gt; void</code> |

**Returns:** <code><a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### MarketingCloudNotification

| Prop            | Type                |
| --------------- | ------------------- |
| **`timestamp`** | <code>number</code> |
| **`message`**   | <code>string</code> |
| **`sfmcType`**  | <code>string</code> |
| **`extras`**    | <code>any</code>    |
| **`action`**    | <code>'tap'</code>  |

</docgen-api>
