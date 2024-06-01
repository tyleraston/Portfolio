import pandas as pd
import matplotlib.pyplot as plt

# Load the dataset
file_path = 'autism_prevalence_studies.csv'
df = pd.read_csv(file_path)
import os

# Create a directory named 'plots' if it doesn't exist
if not os.path.exists('plots'):
    os.makedirs('plots')

# Save the plots to the 'plots' directory
plt.figure(figsize=(10, 6))
df.groupby('Year Published')['ASD Prevalence Estimate per 1,000'].mean().plot()
plt.title('Autism Prevalence Rates Over Time')
plt.xlabel('Year')
plt.ylabel('Prevalence Rate per 1,000')
plt.savefig('plots/prevalence_trends_over_time.png')
plt.show()

plt.figure(figsize=(12, 8))
df.groupby('Country')['ASD Prevalence Estimate per 1,000'].mean().sort_values().plot(kind='bar')
plt.title('Average Autism Prevalence by Country')
plt.xlabel('Country')
plt.ylabel('Prevalence Rate per 1,000')
plt.xticks(rotation=90)
plt.savefig('plots/geographic_variations_in_prevalence.png')
plt.show()

plt.figure(figsize=(8, 5))
df['Male:Female Sex Ratio'].dropna().plot(kind='hist', bins=15, color='skyblue')
plt.title('Distribution of Male:Female Sex Ratio in Autism Prevalence Studies')
plt.xlabel('Male:Female Sex Ratio')
plt.ylabel('Frequency')
plt.savefig('plots/gender_differences_in_prevalence.png')
plt.show()

plt.figure(figsize=(8, 5))
plt.scatter(df['IQ Score <70 (%)'], df['ASD Prevalence Estimate per 1,000'], alpha=0.6)
plt.title('Correlation between IQ Scores and Autism Prevalence')
plt.xlabel('Percentage of IQ Score <70')
plt.ylabel('ASD Prevalence Estimate per 1,000')
plt.savefig('plots/correlation_iq_scores_prevalence.png')
plt.show()
