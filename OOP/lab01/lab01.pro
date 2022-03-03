QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++11

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
    action.cpp \
    controller.cpp \
    figure.cpp \
    functions.cpp \
    io.cpp \
    main.cpp \
    mainwindow.cpp \
    matrix.cpp \
    myscene.cpp \
    points.cpp \
    rc.cpp

HEADERS += \
    action.h \
    controller.h \
    figure.h \
    functions.h \
    io.h \
    mainwindow.h \
    matrix.h \
    myscene.h \
    points.h \
    rc.h

FORMS += \
    mainwindow.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target
