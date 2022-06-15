import type { PluginListenerHandle } from '@capacitor/core';

export interface MarketingCloudNotification {
  sfmcMessageId?: string;
  extras?: any;
  action: 'tap';
}

export interface MarketingCloudPlugin {
  /**
   * Returns true if push notifications are enabled, otherwise false.
   *
   * @since 1.0.0
   */
  isPushEnabled(): Promise<{ value: boolean }>;

  /**
   * Sets push notifications status according to the provided input param.
   *
   * @since 1.0.0
   */
  setPushEnabled(opts: { enabled: boolean }): void;

  /**
   * Get profile id.
   *
   * @since 1.0.0
   */
  getProfileId(): Promise<{ value: string }>;

  /**
   * Set profile id.
   *
   * @since 1.0.0
   */
  setProfileId(opts: { value: string }): void;

  /**
   * Gets attributes.
   *
   * @since 1.0.0
   */
  getAttributes(): Promise<{ attributes: string }>;

  /**
   * Sets an attribute.
   *
   * @since 1.0.0
   */
  setAttribute(opts: { key: string; value: string }): void;

  /**
   * Clears an attribute.
   *
   * @since 1.0.0
   */
  clearAttribute(opts: { key: string }): Promise<{ value: boolean }>;

  /**
   * Gets tags.
   *
   * @since 1.0.0
   */
  getTags(): Promise<{ tags: string[] }>;

  /**
   * Adds a tag.
   *
   * @since 1.0.0
   */
  addTag(opts: { value: string }): Promise<{ value: boolean }>;

  /**
   * Removes a tag.
   *
   * @since 1.0.0
   */
  removeTag(opts: { value: string }): Promise<{ value: boolean }>;

  /**
   * Helper method to identify a push message payload sent from the Marketing Cloud.
   *
   * @since 1.0.0
   */
  isMarketingCloudNotification(opts: { notification: any }): Promise<{ value: boolean }>;

  /**
   * Helper method to notify SFMC that your notification has been opened. When this method is
   * called providing a valid SFMC notificaion then `'notificationOpened'` listener is fired.
   *
   * Only available on iOS.
   *
   * @since 1.0.0
   */
  notifyNotificationOpened(opts: { notification: any }): void;

  /**
   * Shows a SFMC notification. If the method returns false, that means that the provided
   * notification payload is not a valid SFMC one and it could be handled successfully.
   *
   * Only available on Android.
   *
   * @since 1.0.0
   */
  showNotification(opts: { notification: any }): Promise<{ value: boolean }>;

  /**
   * Called when a SFMC notification is opened.
   *
   * Provides the notification opened.
   *
   * @since 1.0.0
   */
  addListener(
    eventName: 'notificationOpened',
    listenerFunc: (notification: MarketingCloudNotification) => void
  ): PluginListenerHandle;
}
