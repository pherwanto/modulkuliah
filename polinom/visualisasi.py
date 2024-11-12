import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Membaca data
df = pd.read_csv('tripadvisor_reviews.csv', sep=';')

# Daftar kategori kolom
categories = df.columns

# Membuat histogram untuk setiap kategori
plt.figure(figsize=(15, 10))
for i, category in enumerate(categories, 1):
    plt.subplot(2, 5, i)  # Plot grid 2x5
    sns.histplot(df[category], bins=10, kde=True)
    plt.title(f'Distribusi Rating {category}')
plt.tight_layout()
plt.show()

# Membuat boxplot untuk setiap kategori
plt.figure(figsize=(15, 10))
sns.boxplot(data=df)
plt.title('Boxplot Distribusi Rating di Semua Kategori')
plt.xticks(rotation=45)
plt.show()
