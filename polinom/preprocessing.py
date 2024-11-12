import pandas as pd
import nltk

# aktifkan untuk pertama kali sehingga di punkt dan punkt_tab bisa download
# nltk.download('punkt')
# nltk.download('punkt_tab')

# Tentukan lokasi penyimpanan untuk nltk_data
nltk.data.path.append('D:\\nltk_data')  # Buat folder di 'D:\\nltk_data'

from sklearn.feature_extraction.text import CountVectorizer, TfidfVectorizer
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
from nltk.tokenize import word_tokenize
import re

# Contoh teks email
email1 = "You won $1000 CASH!"
email2 = "Claim your free cash now!"

# 1. Pembersihan Data (hapus simbol, angka, dll.)
def clean_text(text):
    text = re.sub(r'\d+', '', text)  # Hapus angka
    text = re.sub(r'\W', ' ', text)  # Hapus simbol
    text = text.lower()              # Ubah ke huruf kecil
    return text

email1_clean = clean_text(email1)
email2_clean = clean_text(email2)

# 2. Tokenisasi
tokens_email1 = word_tokenize(email1_clean)
tokens_email2 = word_tokenize(email2_clean)

# 3. Menghapus Stop Words
stop_words = set(stopwords.words('english'))
tokens_email1 = [word for word in tokens_email1 if word not in stop_words]
tokens_email2 = [word for word in tokens_email2 if word not in stop_words]

# 4. Stemming (mengubah kata menjadi bentuk dasar)
ps = PorterStemmer()
tokens_email1 = [ps.stem(word) for word in tokens_email1]
tokens_email2 = [ps.stem(word) for word in tokens_email2]

print("Email 1 setelah pra-pemrosesan:", tokens_email1)
print("Email 2 setelah pra-pemrosesan:", tokens_email2)

# 5. Menghitung Frekuensi Kata (Bag of Words)
emails = [email1_clean, email2_clean]
vectorizer = CountVectorizer()
X = vectorizer.fit_transform(emails)

print("Fitur Bag of Words:\n", vectorizer.get_feature_names_out())
print("Matriks Frekuensi:\n", X.toarray())

# 6. Alternatif: TF-IDF
tfidf_vectorizer = TfidfVectorizer()
X_tfidf = tfidf_vectorizer.fit_transform(emails)

print("Fitur TF-IDF:\n", tfidf_vectorizer.get_feature_names_out())
print("Matriks TF-IDF:\n", X_tfidf.toarray())
