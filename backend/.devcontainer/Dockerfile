FROM mcr.microsoft.com/devcontainers/python:1-3.11-bullseye

WORKDIR /app

RUN pip install pre-commit

COPY requirements_dev.txt /tmp/pip-tmp/
RUN pip --disable-pip-version-check --no-cache-dir install -r /tmp/pip-tmp/requirements_dev.txt