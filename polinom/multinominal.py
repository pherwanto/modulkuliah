import pandas as pd
from scipy.stats import multinomial

# Load the dataset
df = pd.read_csv('tripadvisor_reviews.csv', sep=';')

# Calculate the total number of possible events (sum of all category ratings for each row)
df['total_ratings'] = df.sum(axis=1)

# Define probabilities for each category (equal probabilities across 10 categories)
probabilities = [1/10] * 10  # Equal probability for each of the 10 categories

# Iterate over each user in the dataset and calculate the multinomial probability
for index, row in df.iterrows():
    # Extract the ratings for each category for this user
    x = row[:-1].values  # Exclude the 'total_ratings' column
    
    # Ensure that the sum of x equals the total number of events (ratings)
    n = int(row['total_ratings'])
    
    # Applying multinomial distribution
    prob = multinomial.pmf(x, n=n, p=probabilities)
    
    # Display the probability for this user
    print(f"Probability of user {index + 1} ratings: {prob}")
