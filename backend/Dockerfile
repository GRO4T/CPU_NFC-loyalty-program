FROM python:3.11.5-bullseye

RUN mkdir /app
WORKDIR /app

COPY . /app/

RUN pip install -r requirements.txt

EXPOSE 8000

CMD cd ./nfclp && python manage.py runserver 0.0.0.0:8000
