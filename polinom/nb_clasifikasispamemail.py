# Import libraries
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score, classification_report

# Load dataset
url = 'https://archive.ics.uci.edu/ml/machine-learning-databases/spambase/spambase.data'
data = pd.read_csv(url, header=None)

# Misalkan kita anggap kolom terakhir adalah label (spam = 1, ham = 0)
X = data.iloc[:, :-1]  # Fitur
y = data.iloc[:, -1]   # Label

# Bagi dataset menjadi data latih dan data uji
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Inisialisasi model Multinomial Naive Bayes
clf = MultinomialNB()

# Latih model dengan data latih
clf.fit(X_train, y_train)

# Prediksi dengan data uji
y_pred = clf.predict(X_test)

# Evaluasi model
accuracy = accuracy_score(y_test, y_pred)
report = classification_report(y_test, y_pred, output_dict=True)

# Tampilkan hasil evaluasi numerik
print(f'Akurasi: {accuracy}')
print(classification_report(y_test, y_pred))

# Visualisasi hasil evaluasi dalam chart

# Konversi data classification_report ke DataFrame
df_report = pd.DataFrame(report).transpose()

# Hanya ambil baris kategori spam (1) dan ham (0)
df_report = df_report.loc[['0', '1'], ['precision', 'recall', 'f1-score']]

# Plot metrik evaluasi
df_report.plot(kind='bar', figsize=(10, 6))
plt.title('Precision, Recall, and F1-Score for Ham (0) and Spam (1)')
plt.ylabel('Scores')
plt.ylim(0, 1)
plt.xticks(rotation=0)
plt.show()
