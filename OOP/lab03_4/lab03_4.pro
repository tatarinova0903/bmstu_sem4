QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++17

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
    builder.cpp \
    camera.cpp \
    camera_command.cpp \
    command.cpp \
    composite.cpp \
    details.cpp \
    draw_manager.cpp \
    draw_manager_creator.cpp \
    drawer.cpp \
    facade.cpp \
    link.cpp \
    load_manager_cretor.cpp \
    loader.cpp \
    main.cpp \
    mainwindow.cpp \
    model.cpp \
    model_command.cpp \
    point.cpp \
    reform_manager.cpp \
    reform_manager_creator.cpp \
    scene.cpp \
    scene_command.cpp \
    scene_manager.cpp \
    scene_manager_creator.cpp

HEADERS += \
    abstract_loader.h \
    base_error.h \
    base_manager.h \
    builder.h \
    camera.h \
    camera_command.h \
    camera_error.h \
    command.h \
    composite.h \
    details.h \
    draw_manager.h \
    draw_manager_creator.h \
    drawer.h \
    facade.h \
    factory.h \
    file_error.h \
    iterator.h \
    iterator.hpp \
    link.h \
    load_manager_cretor.h \
    loader.h \
    mainwindow.h \
    model.h \
    model_command.h \
    model_error.h \
    object.h \
    point.h \
    reform_manager.h \
    reform_manager_creator.h \
    scene.h \
    scene_command.h \
    scene_manager.h \
    scene_manager_creator.h \
    vector.h \
    vector.hpp \
    visitor.h

FORMS += \
    mainwindow.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target
