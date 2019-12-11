import torch
from torchvision import models
from torchvision import transforms
from PIL import Image
import os
import io
import socket
import time
import glob

class modelInfer:
    def __init__(self, modeltype='resnet50'):
        with open('classes.txt') as f:
          labels2 = eval(f.read().replace('\n', ''))
        self.labels=[]
        for i in labels2:
            self.labels+=[labels2[i].split(',')[0]]

        self.transform = \
        transforms.Compose([transforms.Resize(256), transforms.CenterCrop(224),
                            transforms.ToTensor(), transforms.Normalize(
                            mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])]) 
        # this transform is prescribed by pytorch documentation for resnet pretrained imagenet dataset networks
        self.mod=modeltype
        if modeltype=='resnet50':
            self.Imodel = models.resnet50(pretrained=True, progress=True) 
            #pretrained resnet50 will be resource efficient while still achieving near state-of-the-art results
        elif modeltype=='resnet34':
            self.Imodel = models.resnet34(pretrained=True, progress=True) 
        elif modeltype=='resnet101':
            self.Imodel = models.resnet101(pretrained=True, progress=True) 
        elif modeltype=='resnet152':
            self.Imodel = models.resnet152(pretrained=True, progress=True) 

        print(modeltype)
        self.listener()


    def model_inference(self, filename):
        timestart=time.time()
        
        with open(filename, "rb") as f:
            b = io.BytesIO(f.read())
            img = Image.open(b)
        
        # img = Image.open(filename)
        os.remove(filename)

        fileout='output/'+filename.split('/')[1].split('.')[0]+'.txt'
        transformed_img = self.transform(img)
        transformed_img = torch.unsqueeze(transformed_img,0) #image transformed

        self.Imodel.eval()
        out = self.Imodel(transformed_img)
        prediction_certainties = torch.nn.functional.softmax(out, dim=1)[0]

        _, ind = torch.sort(out, descending=True)
        #Top five guesses and associated prediction ppercentages
        top5 = [(self.labels[idx], 100.0*prediction_certainties[idx].item()) for idx in ind[0][:5]]
        strng=''
        cnt=0
        choices=['top choice: ','second choice: ','third choice: ','fourth choice: ','fifth choice: ']
        strng+=self.mod+' predicts this image as:\n'
        for i in top5:
            strng+=(choices[cnt] + str(i[0])).ljust(35)
            strng+='with a confidence percentage of '+str(round(i[1],1))+' confidence percentage\n'
            cnt+=1
        timestop=time.time()
        timetot=timestop-timestart
        strng+='_'+str(1000.0*timetot)
        with open(fileout,'w') as f:
          f.write(strng)
        return strng


    def listener(self,wait=.0050):
        counter=0
        while True:
            filename=str(counter)+'.'
            dirinfile='input/'+filename+'*'
            diroutfile='output/'+filename
            if glob.glob(dirinfile):
                 counter+=1#execute
                 self.model_inference(glob.glob(dirinfile)[0])
            else:
                time.sleep(wait)


