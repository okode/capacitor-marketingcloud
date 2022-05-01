export interface MarketingCloudPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
